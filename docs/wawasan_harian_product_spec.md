# Wawasan Harian --- Product & Development Specification

**Document version:** 1.0\
**Status:** Initial implementation brief\
**Platform:** Android\
**Primary language:** Kotlin\
**Target:** A real Android Home Screen widget for daily
general-knowledge learning

------------------------------------------------------------------------

## 1. Product overview

**Wawasan Harian** is a lightweight Android application that helps the
user build general knowledge from the basics through short daily
lessons.

The main product experience is a **real Android Home Screen widget**.
The user should be able to see a daily learning prompt and a short
knowledge snippet without opening the app. Tapping the widget opens the
relevant lesson inside the app.

This is not merely an app icon, launcher shortcut, or a screen that
imitates a widget. It must use Android's actual widget mechanism and be
addable through the Android launcher's widget picker.

### Primary user goal

Help the user become more confident in everyday conversations by
gradually learning about geography, history, science, economics,
government, culture, society, sports, technology, and current affairs.

### Product principles

1.  Start from beginner level and explain concepts in simple Indonesian.
2.  Prefer understanding and conversational confidence over
    memorization.
3.  Keep daily learning short: approximately 15 minutes.
4.  Make the Home Screen widget useful at a glance.
5.  Work offline for previously bundled or downloaded lessons.
6.  Avoid presenting unverified or outdated information as current fact.
7.  Keep the first release small, reliable, and easy to extend.

------------------------------------------------------------------------

## 2. MVP scope

### Must have

-   A native Android application written in Kotlin.
-   A real Home Screen widget using Android AppWidget APIs or Jetpack
    Glance.
-   Widget appears in the Android launcher widget picker.
-   Widget displays:
    -   App name: **Wawasan Harian**
    -   Current lesson title or daily topic
    -   A short fact or learning teaser
    -   Learning progress
    -   A clear action to continue learning
-   Tapping the widget opens the matching lesson in the app.
-   A lesson screen with explanations in beginner-friendly Indonesian.
-   A simple 12-week learning curriculum.
-   Daily lesson progression and completion tracking.
-   Local persistence so progress survives app restarts and device
    reboots.
-   A useful default widget state when the user has not started
    learning.
-   Widget refresh after lesson completion and when the displayed lesson
    changes.
-   Accessibility support for text scaling, content descriptions, and
    touch targets.

### Out of scope for the first MVP

-   User accounts and cloud synchronization.
-   Social features, leaderboards, or public profiles.
-   Payments or subscriptions.
-   AI-generated lessons at runtime.
-   Push notifications requiring a backend.
-   A complex content management system.
-   iOS or web versions.

Keep these out of the MVP unless implementation reveals a genuine
technical necessity.

------------------------------------------------------------------------

## 3. Target user and learning experience

The target user is an adult beginner who wants to improve general
knowledge mainly to feel more confident when talking with other people.

The app should not feel like a school examination application. It should
feel like a friendly daily learning companion.

### Learning session structure

A normal lesson should take around 15 minutes and follow this structure:

1.  **Kenali** --- introduce the topic and its basic concept.
2.  **Ketahui** --- explain important facts and terms.
3.  **Hubungkan** --- connect the topic to daily life in Indonesia.
4.  **Diskusikan** --- provide a conversation prompt or example
    dialogue.
5.  **Uji** --- provide a short quiz, normally 3--5 questions.
6.  **Rangkum** --- show a concise recap and mark the lesson complete.

The user must be able to read a lesson without answering the quiz. Do
not block lesson navigation behind quiz completion.

### Tone and language

-   Default language: Bahasa Indonesia.
-   Friendly, respectful, clear, and conversational.
-   Explain unfamiliar terms when they first appear.
-   Avoid unnecessary jargon and overly academic language.
-   Never shame the user for an incorrect answer.
-   For incorrect answers, explain the correct answer and why it is
    correct.

------------------------------------------------------------------------

## 4. Curriculum: 12 weeks

The initial curriculum contains six learning stages, each lasting two
weeks. The MVP can ship with a small curated set of lessons and expand
the content later.

  ------------------------------------------------------------------------
  Stage                            Weeks Topic            Expected
                                                          conversational
                                                          outcome
  ---------------- --------------------- ---------------- ----------------
  1                                 1--2 Geography and    Can discuss
                                         the world        countries,
                                                          regions,
                                                          capitals,
                                                          continents,
                                                          oceans, and
                                                          Indonesia

  2                                 3--4 History and      Can discuss
                                         civilization     important
                                                          historical
                                                          events,
                                                          kingdoms, and
                                                          figures

  3                                 5--6 Science and      Can explain
                                         everyday life    basic phenomena
                                                          involving the
                                                          body, nature,
                                                          space, and
                                                          technology

  4                                 7--8 Economics and    Can understand
                                         government       basic
                                                          discussions
                                                          about money,
                                                          inflation,
                                                          taxes,
                                                          democracy, and
                                                          institutions

  5                                9--10 Culture and      Can discuss
                                         society          customs,
                                                          languages,
                                                          cultural
                                                          diversity, and
                                                          social life

  6                               11--12 Modern world and Can follow
                                         current topics   discussions
                                                          about
                                                          technology, AI,
                                                          environment,
                                                          geopolitics,
                                                          sports, and
                                                          current affairs
  ------------------------------------------------------------------------

### Suggested weekly rhythm

-   Monday: New lesson
-   Tuesday: Deepen the previous lesson
-   Wednesday: New lesson
-   Thursday: Everyday-life examples
-   Friday: New lesson
-   Saturday: Quiz and conversation practice
-   Sunday: Light review

The schedule is a recommendation, not a restriction. Users must be able
to learn at their own pace.

### Content freshness

Separate evergreen lessons from time-sensitive content.

-   Evergreen: geography basics, scientific concepts, historical
    background, basic economics.
-   Time-sensitive: current office holders, current events, prices,
    rankings, and recent statistics.

Do not hardcode time-sensitive facts into evergreen lessons. For the
MVP, prioritize evergreen content. If current-affairs content is added
later, include a source, publication date, and last-verified date.

------------------------------------------------------------------------

## 5. Widget requirements

### 5.1 Widget identity

-   Name in widget picker: **Wawasan Harian**
-   Recommended initial size: resizable medium widget, approximately 4 ×
    2 cells where supported.
-   Support smaller and larger sizes gracefully.
-   Use the system launcher's widget resizing behavior.
-   Do not assume all Android launchers use identical dimensions.

### 5.2 Widget content

The widget should display the following information where space permits:

1.  App identity: WAWASAN HARIAN.
2.  Daily label, such as "Pelajaran hari ini".
3.  Lesson title, such as "Mengenal Benua".
4.  A short knowledge snippet, ideally 1--3 concise sentences.
5.  Learning progress, such as "Hari 1 dari 84".
6.  A primary action: "Mulai belajar" or "Lanjutkan".
7.  Optional small progress indicator.

For compact widget sizes, prioritize: - Lesson title - One short fact -
Continue-learning action

Do not force every field into the smallest size.

### 5.3 Widget visual direction

Use a calm, modern, readable design.

Suggested visual direction: - Dark navy background. - Warm muted
yellow/gold accent. - White or light neutral text. - Rounded corners. -
Clear hierarchy and generous spacing. - Avoid excessive decoration and
dense text.

Suggested colors (implementation may refine these): - Navy: `#172B43` -
Secondary navy: `#29435F` - Accent gold: `#F2C66D` - Light text:
`#FFFFFF` - Secondary text: `#D6E1EC`

Use Android dynamic color only if it does not reduce readability or
break the intended visual hierarchy. Respect system dark/light mode
where practical.

### 5.4 Widget interactions

-   Tapping the main widget body opens the current lesson.
-   Tapping the primary action opens the current lesson.
-   If a separate "Quiz" action is implemented, it opens the current
    lesson's quiz.
-   Avoid controls that look interactive but do nothing.
-   Do not implement complex interactive controls inside the widget for
    the MVP.

### 5.5 Widget data and refresh

The widget must read the same learning state as the app.

Refresh the widget when: - The app marks a lesson complete. - The
current lesson changes. - The user changes a relevant setting. - The
widget is first added. - Android requests a widget update.

Use supported Android scheduling and widget update mechanisms. Do not
create a permanently running background service just to refresh a daily
widget.

Use WorkManager only where appropriate. Respect Android background
execution limits and battery usage.

If the device is offline, show the latest locally available lesson
content.

### 5.6 Widget empty and error states

If no lesson progress exists: - Show a welcome message. - Show the first
recommended lesson. - Show an action to start learning.

If lesson content cannot be loaded: - Show a friendly fallback
message. - Keep the widget tappable and open the app. - Never show a
blank or broken widget.

------------------------------------------------------------------------

## 6. Application screens

### 6.1 Home screen

Purpose: show the user where they are in the learning journey.

Suggested elements: - Greeting. - Current lesson card. - Continue
learning button. - Weekly learning progress. - 12-week curriculum
overview. - Recently completed lessons.

### 6.2 Lesson detail

Suggested elements: - Stage and lesson number. - Lesson title. -
Estimated reading time. - Main explanation divided into readable
sections. - Key terms and definitions. - Everyday examples. - "Bahan
obrolan" section with conversation prompts. - Previous/next lesson
navigation. - Mark as complete action.

### 6.3 Quiz

Requirements: - 3--5 multiple-choice questions per lesson. - One answer
per question. - Show the result after submission. - Explain each correct
answer. - Allow the user to retry. - Store the latest attempt and best
score locally. - Quiz score must not prevent lesson completion.

### 6.4 Curriculum

Show the six stages and their lessons.

Each lesson should have a clear state: - Not started - In progress -
Completed

Do not imply that the user must finish lessons on a strict calendar
schedule.

### 6.5 Progress

Show simple, encouraging metrics: - Completed lessons. - Current
stage. - Total learning progress. - Recent learning activity.

Avoid guilt-inducing streak messaging. Missing a day must not reset or
penalize the user.

### 6.6 Settings

MVP settings: - Daily reminder on/off (optional for first release). -
Preferred reminder time (only if reminders are implemented). - Reset
learning progress, with a confirmation dialog. - About the app. -
Content version.

------------------------------------------------------------------------

## 7. Suggested technical architecture

Use a native Android architecture. Prefer maintainable, testable code
over unnecessary abstraction.

### Recommended stack

-   Language: Kotlin.
-   UI: Jetpack Compose for app screens, unless the existing project has
    a strong reason to use XML Views.
-   Widget: Jetpack Glance if its supported interactions and layouts
    satisfy the design; otherwise use the Android AppWidget APIs
    directly.
-   Local database: Room.
-   Preferences: DataStore.
-   Background work: WorkManager only when needed.
-   Dependency injection: Hilt or a simpler constructor-injection
    approach. Do not add a DI framework without a clear benefit.
-   Asynchronous work: Kotlin Coroutines and Flow.
-   Minimum SDK: choose based on the intended device coverage; document
    the decision before implementation.
-   Target/compile SDK: use the latest stable SDK available in the
    development environment.

Do not use a web page, notification, floating overlay, or launcher
shortcut as a substitute for a real Android widget.

### Layering

Suggested package organization:

``` text
com.example.wawasanharian/
├── app/
│   ├── MainActivity.kt
│   └── navigation/
├── core/
│   ├── database/
│   ├── datastore/
│   ├── model/
│   └── common/
├── data/
│   ├── local/
│   ├── repository/
│   └── seed/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── feature/
│   ├── home/
│   ├── lesson/
│   ├── quiz/
│   ├── curriculum/
│   ├── progress/
│   └── settings/
└── widget/
    ├── WawasanWidget.kt
    ├── WawasanWidgetReceiver.kt
    ├── WidgetDataProvider.kt
    └── WidgetUpdateManager.kt
```

This is a suggested structure, not a requirement to create empty
packages. Adapt it to the actual project and avoid overengineering.

### Data flow

``` text
Compose UI ───────────────┐
                         │
                         ▼
                   Use Cases
                         │
                         ▼
                    Repository
                         │
             ┌───────────┴───────────┐
             ▼                       ▼
          Room                  DataStore
             │                       │
             └───────────┬───────────┘
                         ▼
                 Shared lesson state
                         │
                         ▼
                  Widget data provider
                         │
                         ▼
                 Android Home Screen
```

The widget and app must use a single source of truth for lesson
progress. Do not maintain separate, conflicting progress state for the
widget.

------------------------------------------------------------------------

## 8. Suggested data model

The implementation agent should refine the schema before coding and
explain any changes.

### LearningStage

-   `id`
-   `title`
-   `description`
-   `order`

### Lesson

-   `id`
-   `stageId`
-   `title`
-   `summary`
-   `estimatedMinutes`
-   `order`
-   `contentVersion`
-   `isPublished`

### LessonSection

-   `id`
-   `lessonId`
-   `title`
-   `body`
-   `order`

### KeyTerm

-   `id`
-   `lessonId`
-   `term`
-   `definition`

### ConversationPrompt

-   `id`
-   `lessonId`
-   `prompt`
-   `exampleResponse`
-   `order`

### QuizQuestion

-   `id`
-   `lessonId`
-   `question`
-   `explanation`
-   `order`

### QuizOption

-   `id`
-   `questionId`
-   `label`
-   `isCorrect`
-   `order`

### UserLessonProgress

-   `lessonId`
-   `status`
-   `startedAt`
-   `completedAt`
-   `lastOpenedAt`

### QuizAttempt

-   `id`
-   `lessonId`
-   `score`
-   `totalQuestions`
-   `attemptedAt`

### UserPreferences

-   `dailyReminderEnabled`
-   `dailyReminderTime`
-   `lastKnownContentVersion`

For a single-user offline MVP, user identity is unnecessary. Do not add
account or authentication tables.

------------------------------------------------------------------------

## 9. Initial content requirements

Create a small, high-quality seed curriculum for the MVP.

Minimum suggested content: - 6 stages. - At least 2 lessons per stage
(12 lessons total). - Each lesson has: - A clear title. - A short
summary. - 3--5 explanatory sections. - 3--5 key terms where useful. -
At least 2 everyday examples. - At least 2 conversation prompts. - 3--5
quiz questions with explanations.

Content must be accurate and written for beginners.

### Initial lesson suggestions

1.  Apa itu bumi, benua, dan samudra?
2.  Mengenal Indonesia dan negara-negara tetangga.
3.  Apa perbedaan negara, bangsa, dan wilayah?
4.  Mengenal kerajaan-kerajaan besar di Indonesia.
5.  Bagaimana manusia mulai mengenal tulisan dan peradaban?
6.  Mengapa terjadi pergantian siang dan malam?
7.  Mengenal organ tubuh manusia.
8.  Apa itu internet, komputer, dan kecerdasan buatan?
9.  Apa itu uang dan bagaimana cara kerjanya?
10. Apa itu inflasi dan mengapa harga barang naik?
11. Mengenal budaya dan keberagaman Indonesia.
12. Bagaimana cara memahami berita dan membedakan fakta dari opini?

Do not invent historical, scientific, or statistical claims. Review
content for accuracy before including it in the seed data.

------------------------------------------------------------------------

## 10. Notifications and reminders

This is optional for the MVP.

If implemented: - Let the user enable or disable reminders. - Let the
user choose a preferred time. - Use a supported Android scheduling
API. - Respect notification permission requirements on relevant Android
versions. - A reminder should be gentle, for example: "Ada wawasan baru
untuk dipelajari hari ini." - Do not send repeated reminders after the
user dismisses one. - Do not make notifications the only way to access
learning content.

The Home Screen widget must remain useful even if notifications are
disabled.

------------------------------------------------------------------------

## 11. Privacy, permissions, and offline behavior

The MVP should not request unnecessary permissions.

Expected: - No location permission. - No contacts permission. - No
microphone or camera permission. - No account sign-in. - No analytics
SDK unless explicitly approved. - No network permission unless a real
feature requires it.

All seed lessons and learning progress should work offline.

If future versions download content: - Explain why internet access is
needed. - Cache content locally. - Keep previously downloaded lessons
accessible offline. - Handle failed downloads gracefully.

------------------------------------------------------------------------

## 12. Accessibility and UX quality

-   Support Android font scaling.
-   Maintain readable contrast.
-   Provide content descriptions for meaningful icons and widget
    actions.
-   Use touch targets appropriate for Android accessibility guidance.
-   Do not communicate completion or correctness through color alone.
-   Avoid long, unbroken paragraphs.
-   Ensure the widget remains readable at supported sizes.
-   Test both light and dark system themes.
-   Ensure the app works with TalkBack for core flows.

------------------------------------------------------------------------

## 13. Testing requirements

### Unit tests

-   Lesson progression rules.
-   Lesson completion.
-   Quiz scoring.
-   Progress calculation.
-   Repository behavior.
-   Default lesson selection.
-   Widget data mapping.

### Instrumentation/UI tests

-   App launches successfully.
-   User can open a lesson.
-   User can complete a lesson.
-   Progress remains after app restart.
-   Quiz answers and explanations render correctly.
-   Reset progress requires confirmation.

### Widget tests / manual verification

-   Widget provider is registered correctly.
-   Widget appears in the launcher widget picker.
-   User can add it to the Home Screen.
-   Widget renders after being added.
-   Tapping the widget opens the correct lesson.
-   Widget updates after lesson completion.
-   Widget handles missing or empty progress.
-   Widget resizes without clipped or unreadable text.
-   Widget displays cached information offline.
-   Widget continues to work after device reboot.

Test on at least one physical Android device and, if available, an
emulator. Record device model, Android version, launcher, and widget
size used during manual testing.

------------------------------------------------------------------------

## 14. Definition of Done

The MVP is complete only when all of the following are true:

-   [ ] Android project builds successfully.
-   [ ] App installs and launches on a physical Android device.
-   [ ] A real Wawasan Harian widget is registered with Android.
-   [ ] Widget appears in the launcher widget picker.
-   [ ] User can add the widget to the Home Screen.
-   [ ] Widget shows the current lesson and a useful short fact.
-   [ ] Widget opens the correct lesson when tapped.
-   [ ] User can read lessons in beginner-friendly Indonesian.
-   [ ] User can complete lessons and take quizzes.
-   [ ] Progress is saved locally and survives app restarts.
-   [ ] Widget refreshes after relevant progress changes.
-   [ ] Empty, offline, and error states are handled.
-   [ ] Core flows have automated tests.
-   [ ] No unnecessary permissions are requested.
-   [ ] README explains how to build, install, test, and add the widget.

A screenshot of an in-app mockup is not proof that the Android widget
works. Verify the actual widget on the launcher.

------------------------------------------------------------------------

## 15. Implementation phases for the coding agent

Work in small, verifiable phases. Do not attempt to generate the entire
application in one pass.

### Phase 0 --- Repository inspection and technical decisions

1.  Inspect the existing repository and development environment.
2.  Identify whether this is a new project or an existing Android
    project.
3.  Check Gradle, Kotlin, Android Gradle Plugin, SDK, and dependency
    compatibility.
4.  Propose the minimum viable architecture.
5.  Decide between Jetpack Glance and Android AppWidget APIs based on
    actual requirements and compatibility.
6.  Document decisions and risks.
7.  Do not rewrite unrelated files.

**Deliverable:** technical plan and dependency list.

### Phase 1 --- Android project foundation

1.  Set up the application module and package structure.
2.  Configure build variants and app identity.
3.  Add Compose, Room, DataStore, and required dependencies only.
4.  Create app theme and navigation foundation.
5.  Confirm debug build and installation.

**Deliverable:** a buildable app shell.

### Phase 2 --- Local data and seed curriculum

1.  Define entities and relationships.
2.  Implement Room database and migrations.
3.  Add seed content for the initial lessons.
4.  Implement repositories and domain use cases.
5.  Add tests for lesson retrieval and progress.

**Deliverable:** locally available curriculum and tested data layer.

### Phase 3 --- Learning experience

1.  Build Home screen.
2.  Build Curriculum screen.
3.  Build Lesson detail screen.
4.  Build Quiz screen.
5.  Build Progress screen.
6.  Implement completion and retry behavior.
7.  Verify local persistence.

**Deliverable:** complete offline learning flow.

### Phase 4 --- Real Android widget

1.  Implement the actual Android widget provider/Glance widget.
2.  Register widget metadata and preview resources.
3.  Implement widget data mapping from the shared repository/state.
4.  Add correct PendingIntent or Glance action navigation.
5.  Implement empty and error states.
6.  Implement refresh after progress changes.
7.  Verify the widget in the Android launcher widget picker.
8.  Test multiple sizes and device restart behavior.

**Deliverable:** functioning Home Screen widget on a real Android
device.

### Phase 5 --- Polish and quality

1.  Add accessibility support.
2.  Validate light and dark themes.
3.  Review text layout at different font scales.
4.  Add tests and fix defects.
5.  Check battery and background-work behavior.
6.  Remove unused dependencies and code.
7.  Update README and screenshots.

**Deliverable:** MVP release candidate.

### Phase 6 --- Optional enhancements

Only after the MVP is stable: - Daily reminders. - More lessons and
richer quizzes. - Downloadable content packs. - Current-affairs content
with sources and verification dates. - Backup and restore. - Cloud
synchronization. - AI-assisted discussion practice.

------------------------------------------------------------------------

## 16. Instructions for the agentic AI

Act as a senior Android engineer and implement this product
incrementally.

### Working rules

1.  Start by inspecting the repository. Never assume its structure or
    dependency versions.
2.  Before changing files, summarize the implementation plan for the
    current phase.
3.  Implement only the current phase. Do not silently skip ahead.
4.  Prefer stable Android APIs and compatible dependency versions.
5.  Do not invent APIs, Gradle versions, or dependency versions. Verify
    them against the project and official documentation available in the
    environment.
6.  Avoid unnecessary architecture, libraries, abstractions, and
    permissions.
7.  Keep business logic out of UI and widget rendering code.
8.  Use one source of truth for learning progress.
9.  Do not fake widget functionality with a shortcut, notification, or
    in-app card.
10. Run relevant tests and build tasks after each phase.
11. When a build or test fails, investigate the root cause and fix it
    before declaring the phase complete.
12. Do not report a feature as complete unless it has been implemented
    and verified.
13. Do not overwrite user changes or unrelated project files.
14. Keep code readable and add comments only where they clarify
    non-obvious decisions.
15. Update README and implementation notes as the project evolves.

### Required report after each phase

Provide: - Phase name and status. - Summary of implemented features. -
Important files created or modified. - Dependencies added or changed. -
Build and test commands executed. - Actual test/build results. - Known
issues or limitations. - Exact next recommended phase.

### First instruction

**Begin with Phase 0 only.** Inspect the repository, identify the
existing Android setup, assess Jetpack Glance versus Android AppWidget
APIs, and return a concise technical plan. Do not start implementation
until the Phase 0 findings and decisions are clear.

------------------------------------------------------------------------

## 17. Official Android documentation references

Use current official documentation during implementation. Confirm
compatibility with the project's actual SDK and dependency versions.

-   App widgets overview:
    https://developer.android.com/develop/ui/views/appwidgets/overview
-   Build an app widget:
    https://developer.android.com/develop/ui/views/appwidgets
-   Jetpack Glance:
    https://developer.android.com/develop/ui/compose/glance
-   Room: https://developer.android.com/training/data-storage/room
-   DataStore:
    https://developer.android.com/topic/libraries/architecture/datastore
-   WorkManager:
    https://developer.android.com/topic/libraries/architecture/workmanager
-   Android accessibility:
    https://developer.android.com/guide/topics/ui/accessibility

------------------------------------------------------------------------

## 18. Product success criteria

The product succeeds when the user can:

1.  See a useful learning prompt on the Android Home Screen.
2.  Open the lesson with one tap.
3.  Understand the material without needing prior knowledge.
4.  Relate the lesson to real-life conversations.
5.  Practice explaining what they learned.
6.  Track progress without pressure.
7.  Continue learning even when offline.

**Core product promise:** Belajar sedikit setiap hari, wawasan bertambah
setiap saat.
