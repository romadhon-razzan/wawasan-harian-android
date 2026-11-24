package id.co.ptn.wawasanharian.data.seed

import id.co.ptn.wawasanharian.data.local.ContentBundle
import id.co.ptn.wawasanharian.data.local.ConversationPromptEntity
import id.co.ptn.wawasanharian.data.local.EverydayExampleEntity
import id.co.ptn.wawasanharian.data.local.KeyTermEntity
import id.co.ptn.wawasanharian.data.local.LearningStageEntity
import id.co.ptn.wawasanharian.data.local.LessonEntity
import id.co.ptn.wawasanharian.data.local.LessonSectionEntity
import id.co.ptn.wawasanharian.data.local.QuizOptionEntity
import id.co.ptn.wawasanharian.data.local.QuizQuestionEntity
import id.co.ptn.wawasanharian.domain.SectionKind

data class SeedStage(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<SeedLesson>,
)

data class SeedLesson(
    val id: String,
    val title: String,
    val summary: String,
    val widgetTeaser: String,
    val sections: List<SeedSection>,
    val keyTerms: List<SeedKeyTerm>,
    val examples: List<String>,
    val prompts: List<SeedPrompt>,
    val quiz: List<SeedQuestion>,
    val estimatedMinutes: Int = 15,
)

data class SeedSection(val kind: SectionKind, val title: String, val body: String)
data class SeedKeyTerm(val term: String, val definition: String)
data class SeedPrompt(val prompt: String, val exampleResponse: String)
data class SeedQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

// Small helpers that keep the curriculum files readable.
internal fun kenali(title: String, body: String) = SeedSection(SectionKind.KENALI, title, body.trimIndent())
internal fun ketahui(title: String, body: String) = SeedSection(SectionKind.KETAHUI, title, body.trimIndent())
internal fun hubungkan(title: String, body: String) = SeedSection(SectionKind.HUBUNGKAN, title, body.trimIndent())
internal fun rangkum(body: String) = SeedSection(SectionKind.RANGKUM, "Rangkuman", body.trimIndent())
internal fun term(term: String, definition: String) = SeedKeyTerm(term, definition)
internal fun prompt(prompt: String, exampleResponse: String) = SeedPrompt(prompt, exampleResponse)
internal fun question(question: String, options: List<String>, correctIndex: Int, explanation: String) =
    SeedQuestion(question, options, correctIndex, explanation)

/** Converts seed content to Room entities with stable, derived ids. */
fun List<SeedStage>.toContentBundle(contentVersion: Int): ContentBundle {
    val stages = mutableListOf<LearningStageEntity>()
    val lessons = mutableListOf<LessonEntity>()
    val sections = mutableListOf<LessonSectionEntity>()
    val keyTerms = mutableListOf<KeyTermEntity>()
    val examples = mutableListOf<EverydayExampleEntity>()
    val prompts = mutableListOf<ConversationPromptEntity>()
    val questions = mutableListOf<QuizQuestionEntity>()
    val options = mutableListOf<QuizOptionEntity>()

    forEachIndexed { stageIndex, stage ->
        stages += LearningStageEntity(stage.id, stage.title, stage.description, stageIndex + 1)
        stage.lessons.forEachIndexed { lessonIndex, lesson ->
            val lid = lesson.id
            lessons += LessonEntity(
                id = lid,
                stageId = stage.id,
                title = lesson.title,
                summary = lesson.summary,
                widgetTeaser = lesson.widgetTeaser,
                estimatedMinutes = lesson.estimatedMinutes,
                sortOrder = lessonIndex + 1,
                contentVersion = contentVersion,
                isPublished = true,
            )
            lesson.sections.forEachIndexed { i, s ->
                sections += LessonSectionEntity("$lid-sec-${i + 1}", lid, s.kind.name, s.title, s.body, i + 1)
            }
            lesson.keyTerms.forEachIndexed { i, t ->
                keyTerms += KeyTermEntity("$lid-term-${i + 1}", lid, t.term, t.definition, i + 1)
            }
            lesson.examples.forEachIndexed { i, e ->
                examples += EverydayExampleEntity("$lid-ex-${i + 1}", lid, e, i + 1)
            }
            lesson.prompts.forEachIndexed { i, p ->
                prompts += ConversationPromptEntity("$lid-cp-${i + 1}", lid, p.prompt, p.exampleResponse, i + 1)
            }
            lesson.quiz.forEachIndexed { i, q ->
                val qid = "$lid-q-${i + 1}"
                questions += QuizQuestionEntity(qid, lid, q.question, q.explanation, i + 1)
                q.options.forEachIndexed { j, label ->
                    options += QuizOptionEntity("$qid-o-${j + 1}", qid, label, j == q.correctIndex, j + 1)
                }
            }
        }
    }
    return ContentBundle(stages, lessons, sections, keyTerms, examples, prompts, questions, options)
}
