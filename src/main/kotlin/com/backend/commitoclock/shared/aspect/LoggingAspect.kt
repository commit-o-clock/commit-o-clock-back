package com.backend.commitoclock.shared.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.ContentCachingRequestWrapper

@Aspect
@Component
class LoggingAspect {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val objectMapper = ObjectMapper().apply { enable(SerializationFeature.INDENT_OUTPUT) }

    @Around("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    fun logHttpRequests(joinPoint: ProceedingJoinPoint): Any? {
        val attributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request = attributes?.request
        val response = attributes?.response

        val methodName = joinPoint.signature.toShortString()

        // ContentCachingRequestWrapper에서 요청 바디 읽기
        val body = if (request is ContentCachingRequestWrapper) {
            String(request.contentAsByteArray)
        } else {
            "Body not available. Ensure the filter is correctly applied."
        }

        logRequest(request, methodName, joinPoint.args, body)

        return try {
            val result = joinPoint.proceed()
            logResponse(response, result)
            result
        } catch (ex: Throwable) {
            logException(ex)
            throw ex
        }
    }

    private fun logRequest(
        request: HttpServletRequest?,
        methodName: String,
        args: Array<Any>,
        body: String
    ) {
        request?.let {
            logger.info("[COMMIT-O-CLOCK REQUEST] HTTP Method: ${it.method}, URL: ${it.requestURI}")
            logger.info(
                "[COMMIT-O-CLOCK REQUEST] Headers: ${
                    it.headerNames.toList().associateWith { header -> it.getHeader(header) }
                }"
            )
            logger.info("[COMMIT-O-CLOCK REQUEST] Body: $body")
            logger.info("[COMMIT-O-CLOCK REQUEST] Method: $methodName, Args: ${toPrettyJson(args)}")
        }
    }

    private fun logResponse(response: HttpServletResponse?, result: Any?) {
        response?.let {
            logger.info("[COMMIT-O-CLOCK RESPONSE] Status: ${it.status}")
        }
        logger.info("[COMMIT-O-CLOCK RESPONSE] Result: ${toPrettyJson(result)}")
    }

    private fun logException(ex: Throwable) {
        logger.error("[COMMIT-O-CLOCK ERROR] Exception: ${ex.message}", ex)
    }

    private fun toPrettyJson(data: Any?): String {
        return try {
            objectMapper.writeValueAsString(data)
        } catch (e: Exception) {
            "Unable to convert to JSON: ${e.message}"
        }
    }
}
