package com.backend.commitoclock.notification.domain.model

enum class Messages(
    private val koreanVersion: String,
    private val englishVersion: String
) {
    NO_COMMIT_TODAY(
        "%s님, 오늘 커밋이 없네요, 코드의 흔적을 남겨보세요!",
        "%s, no commits today? Leave a trace of your code!"
    ),
    END_DAY_WITH_COMMIT(
        "%s님, 커밋 알람입니다! 오늘 커밋으로 하루를 마무리해 보세요!",
        "%s, commit alert! End your day with a commit!"
    ),
    DONT_BE_FORGOTTEN(
        "%s님, 오늘 커밋 안 하면 잊혀질지도 몰라요. 커밋하세요!",
        "%s, no commit today? You might be forgotten. Make a commit!"
    ),
    DONT_FORGET_TO_COMMIT(
        "%s님, 커밋한 지 오래됐네요. 잊지 말고 커밋해요!",
        "%s, it’s been a while since your last commit. Don’t forget to commit!"
    ),
    PRODUCTIVE_DAY(
        "%s님, 오늘도 커밋으로 생산적인 하루를 완성해보세요!",
        "%s, complete your productive day with a commit today!"
    ),
    GITHUB_WAITING(
        "%s님, 깃허브가 오늘 커밋을 기다리고 있어요. 놓치지 마세요!",
        "%s, GitHub is waiting for your commit today. Don’t miss it!"
    ),
    CONSISTENCY_IS_POWER(
        "%s님, 꾸준함은 힘이 됩니다! 오늘의 커밋도 함께 쌓아 볼까요?",
        "%s, consistency is power! Let’s stack today’s commit together!"
    ),
    DAY_WITHOUT_COMMIT(
        "%s님, 커밋 없는 하루는 상상할 수 없어요. 코드 흔적 남기러 가요!",
        "%s, a day without a commit? Unthinkable. Go leave your coding mark!"
    ),
    COMMIT_CHALLENGE(
        "%s님, 커밋 챌린지! 오늘의 미션을 달성하세요!",
        "%s, commit challenge! Complete today’s mission!"
    ),
    LEAVE_YOUR_MARK(
        "%s님, 오늘 깃허브에 흔적 남기는 걸 잊으셨나봐요? 지금 바로 커밋 하러 가 볼까요?",
        "%s, did you forget to leave your mark on GitHub today? If so, how about committing now?"
    );

    companion object {
        fun getMessage(
            name: String,
            language: Countries
        ): String {
            return if (language == Countries.KOREA)
                entries.toTypedArray().random().koreanVersion.format(name)
            else entries.toTypedArray().random().englishVersion.format(name)
        }
    }
}
