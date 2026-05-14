# 系統設計圖 (Diagrams)

> 專案:外國旅客在台灣初期探索文化的體驗設計
> 主要 Persona:深度記錄型外國旅客(requirements.md §1.1)
> 來源:`requirements.md`(FR-02 ~ FR-27 缺 FR-01/15/16/20/23、UC2 ~ UC22 缺 UC1/14/15)
> 內容:Activity Diagram、Domain Class Diagram、System Sequence Diagram (SSD)

---

## 1. Activity Diagrams

依旅客旅程的四個主要階段(啟動 / 規劃 / 探索 / 回顧)與內容維運流程,共繪製 5 張 activity diagram。

### 1.1 啟動與興趣設定 (Onboarding)

對應 UC2、UC13,以及 FR-02、FR-09。

```mermaid
flowchart TD
    Start([首次開啟 App]) --> A5[App 啟動]
    A5 --> A6[選擇語言<br/>繁中 / 簡中 / 英]
    A6 --> A7[選擇文化興趣<br/>歷史 / 美食 / 建築 / 宗教]
    A7 --> A8{是否多主題複選?}
    A8 -- 是 --> A9[儲存多主題與子標籤]
    A8 -- 否 --> A10[儲存單一主題與子標籤]
    A9 --> A11[建立個人偏好檔]
    A10 --> A11
    A11 --> A12[呼叫內容服務取得推薦路線]
    A12 --> A13{是否有可推薦結果?}
    A13 -- 否 --> A14[顯示官方精選路線替代]
    A13 -- 是 --> A15[顯示主題化推薦路線清單]
    A14 --> End([進入規劃階段])
    A15 --> End
```

---

### 1.2 行程規劃 (Trip Planning)

對應 UC4、UC5,以及 FR-03、FR-03a、FR-04、FR-19、FR-22。

```mermaid
flowchart TD
    Start([開始規劃]) --> P1[查看主題路線推薦]
    P1 --> P2{使用推薦路線<br/>或自選景點?}
    P2 -- 推薦 --> P3[選擇主題路線<br/>含跨時期 / 跨類型]
    P2 -- 自選 --> P4[搜尋並挑選景點]
    P3 --> P5[加入景點到行程]
    P4 --> P5
    P5 --> P6[在地圖上排序景點]
    P6 --> P7[以 tag 顏色路徑視覺化]
    P7 --> P8[呼叫地圖服務計算路徑]
    P8 --> P9{交通是否合理?<br/>含山路 / 換車}
    P9 -- 否 --> P10[提示替代景點或順序]
    P10 --> P6
    P9 -- 是 --> P13{使用者是否滿意?}
    P13 -- 否 --> P14[調整景點順序或刪除]
    P14 --> P6
    P13 -- 是 --> P16[儲存行程]
    P16 --> End([完成規劃,進入現場探索])
```

---

### 1.3 現場探索與 Heritage Mission (On-site Exploration)

對應 UC3、UC6、UC7、UC8、UC19、UC20、UC21,以及 FR-05、FR-06、FR-07、FR-08、FR-10、FR-24、FR-25、FR-26。

```mermaid
flowchart TD
    Start([啟程前往景點]) --> E1[App 取得目前位置]
    E1 --> E2[計算導航至下一景點]
    E2 --> E3{移動中經過<br/>其他文化景點?}
    E3 -- 是 --> E4[推送輕量情境卡<br/>例如『此處為赤崁樓』]
    E3 -- 否 --> E5{進入古蹟<br/>地理範圍?}
    E4 --> E5
    E5 -- 否 --> E2
    E5 -- 是 --> M1[Mission Trigger:<br/>推送 Heritage Mission 入口]
    M1 --> M2[顯示 Mission 結構:<br/>X 個必拍 photo spot,進度 Y/X]
    M2 --> M3{選擇 photo spot}
    M3 --> M4[顯示 Story Line 三件套:<br/>Suggested Shot / Why Special /<br/>Memory Prompt]
    M4 --> M5[顯示取景框 overlay<br/>+ 構圖提示]
    M5 --> M6{拍照?}
    M6 -- 是 --> M7[拍攝並附掛至 photo spot]
    M6 -- 否(略過) --> M11
    M7 --> M8{填寫 Memory Prompt?<br/>(選填,鼓勵)}
    M8 -- 填 --> M9[儲存 MemoryAnswer]
    M8 -- 跳過 --> M10[僅儲存照片 + 標籤]
    M9 --> M11[更新 Mission 進度]
    M10 --> M11
    M11 --> E7{是否查看脈絡關聯<br/>(與前後景點)?}
    E7 -- 是 --> E8[顯示時代 / 宗教 / 功能差異]
    E7 -- 否 --> M12{所有 photo spot 完成?}
    E8 --> M12
    M12 -- 否,繼續 --> M3
    M12 -- 部分/全部完成 --> E15{行程結束?}
    E15 -- 否 --> E2
    E15 -- 是 --> End([進入回顧階段])
```

> 重點:Mission 不強制完成所有 photo spot,只完成 1/3 仍可登記為「造訪過」並進入下個景點(對應 FR-25 + Persona「省力」需求);Memory Prompt 全程選填,但填寫後可被 FR-13 敘事生成優先引用。

---

### 1.4 旅程回顧與策展分享 (Review & Curate)

對應 UC9、UC10、UC11、UC12、UC22,以及 FR-11、FR-12、FR-13、FR-14、FR-26、FR-27。

```mermaid
flowchart TD
    Start([開啟『足跡地圖』]) --> R1{是否已有足跡資料?}
    R1 -- 否 --> R2[顯示替代內容<br/>精選他人足跡 / 官方路線]
    R2 --> End1([結束回顧])
    R1 -- 是 --> R3[載入已訪景點 + 標籤 + 照片 +<br/>MemoryAnswer + Mission 進度]
    R3 --> R4[呼叫分類引擎自動分群<br/>廟宇 / 商業 / 歷史]
    R4 --> R5[在地圖呈現足跡 + 時間軸 +<br/>Mission 完成度]
    R5 --> R6{補填 Memory Prompt<br/>或補充照片?}
    R6 -- 是 --> R7[編輯該 photo spot<br/>足跡 / MemoryAnswer]
    R7 --> R5
    R6 -- 否 --> R8{是否生成旅程敘事?}
    R8 -- 否 --> R9[僅查看分類視覺化]
    R9 --> End2([結束])
    R8 -- 是 --> R10[呼叫敘事生成服務<br/>足跡 + 文化脈絡 +<br/>MemoryAnswer + Mission 進度]
    R10 --> R11[顯示明信片式敘事預覽]
    R11 --> R12{是否分享?}
    R12 -- 否 --> End3([僅保存敘事])
    R12 -- 是 --> R13[選擇策展版型<br/>明信片 / 時間軸 / 故事書]
    R13 --> R14[勾選保留哪些 photo /<br/>MemoryAnswer 對外公開]
    R14 --> R15[選擇輸出格式<br/>9:16 / 1:1 / 連結 / 地圖路線]
    R15 --> R16[經使用者確認與匿名化]
    R16 --> R17[發佈到社群或傳給朋友]
    R17 --> End4([完成分享])
```

---

### 1.5 內容維運與 UGC 標籤審核 (Content Management)

對應 UC16、UC17、UC18,以及 FR-18、FR-21。

```mermaid
flowchart TD
    Start([內容後台 / 導覽者登入]) --> C1{操作類型?}
    C1 -- 新增/編輯景點 --> C2[填寫景點基本資訊]
    C2 --> C3[撰寫故事化內容<br/>時代 / 宗教 / 功能]
    C3 --> C4[建立多語版本<br/>繁中 / 簡中 / 英]
    C4 --> C5[送交多語人工校對]
    C5 --> C6{校對通過?}
    C6 -- 否 --> C7[退回修訂]
    C7 --> C3
    C6 -- 是 --> C8[發佈景點]
    C1 -- 維護脈絡關聯 --> C9[建立跨景點關聯<br/>時代 / 主題 / 宗教]
    C9 --> C10[儲存關聯供 UC7 使用]
    C1 -- UGC 標籤審核 --> C11[載入旅客提交的標籤]
    C11 --> C12{是否合理?}
    C12 -- 否 --> C13[拒絕]
    C12 -- 是 --> C14[寫入標籤資料庫]
    C8 --> End([完成])
    C10 --> End
    C13 --> End
    C14 --> End
```

---

## 2. Domain Class Diagram

依需求文件提取核心 domain 實體與其關聯。涵蓋使用者、興趣偏好、景點與故事、標籤層次、路線與行程、足跡與敘事、商家合作、多語內容等。

```mermaid
classDiagram
    direction LR

    class User {
        +String userId
        +String displayName
        +Locale preferredLanguage
        +Persona personaType
        +Date createdAt
        +setPreferences()
        +switchLanguage()
    }

    class Preference {
        +String preferenceId
        +Set~Tag~ interestTags
        +String pace
        +update()
    }

    class Tag {
        +String tagId
        +String name
        +TagType type
        +update()
    }

    class SubTag {
        +String subTagId
        +String name
    }

    class Spot {
        +String spotId
        +String name
        +GeoPoint location
        +SpotCategory category
        +String era
        +String religion
        +String[] images
        +getStory(locale)
    }

    class Story {
        +String storyId
        +String title
        +String body
        +Locale locale
        +String culturalNote
        +translate(locale)
    }

    class CulturalContext {
        +String contextId
        +String dimension
        +String description
        +link(spotA, spotB)
    }

    class Route {
        +String routeId
        +String title
        +RouteType type
        +Boolean isCrossEra
        +generate(themes)
    }

    class Trip {
        +String tripId
        +Date startDate
        +Date endDate
        +Duration totalDuration
        +addSpot(spot)
        +reorder()
    }

    class TripItem {
        +Integer order
        +Duration suggestedStay
    }

    class NavigationLeg {
        +String legId
        +TransportMode mode
        +Duration travelTime
        +String[] transferHints
    }

    class FootprintEntry {
        +String entryId
        +Date visitedAt
        +Set~Tag~ tags
        +String[] photos
        +String moodKeyword
        +tagSpot()
    }

    class Mission {
        +String missionId
        +String title
        +Integer requiredPhotoSpots
        +Boolean partialCompletionAllowed
        +trigger(spotId, userLoc)
        +progress(userId)
    }

    class PhotoSpot {
        +String photoSpotId
        +String name
        +GeoPoint location
        +String suggestedShot
        +String whySpecial
        +Integer orderInMission
        +getGuidance()
    }

    class MemoryPrompt {
        +String promptId
        +String question
        +String style
        +Boolean optional
    }

    class MemoryAnswer {
        +String answerId
        +String body
        +Visibility visibility
        +Date answeredAt
        +edit()
        +setVisibility()
    }

    class MissionProgress {
        +String progressId
        +Integer completed
        +Integer total
        +Date lastUpdated
    }

    class StoryCard {
        +String cardId
        +String triggerType
        +String content
        +pushOnPassBy()
    }

    class Narrative {
        +String narrativeId
        +NarrativeStyle style
        +String body
        +String shareableUrl
        +generate()
        +export()
    }

    class ShareArtifact {
        +String artifactId
        +ShareFormat format
        +Boolean anonymized
        +publish()
    }

    class CulturalGuide {
        +String guideId
        +String[] specialties
        +editSpot()
        +editContext()
    }

    class ContentAdmin {
        +String adminId
        +reviewTranslation()
        +approveUGCTag()
    }

    class Translation {
        +String translationId
        +Locale locale
        +String content
        +Boolean humanReviewed
    }

    User "1" -- "1" Preference : has
    Preference "*" -- "*" Tag : selects
    Tag "1" o-- "*" SubTag : contains

    User "1" -- "*" Trip : owns
    Trip "1" *-- "*" TripItem : composed of
    TripItem "*" -- "1" Spot : refers to
    TripItem "1" -- "0..1" NavigationLeg : nextLeg

    Route "1" -- "*" Spot : sequences
    Trip "0..1" --> "0..1" Route : derivedFrom
    Route "*" -- "*" Tag : taggedBy

    Spot "1" -- "*" Story : narratedBy
    Spot "*" -- "*" Tag : labelledBy
    Spot "*" -- "*" CulturalContext : participatesIn
    CulturalContext "*" -- "*" Spot : connects

    User "1" -- "*" FootprintEntry : records
    FootprintEntry "*" -- "1" Spot : at
    FootprintEntry "*" -- "*" Tag : tagged

    StoryCard "*" -- "1" Spot : about
    StoryCard "*" -- "*" Route : appearsOn

    Spot "1" -- "0..*" Mission : hosts
    Mission "1" *-- "*" PhotoSpot : contains
    PhotoSpot "1" -- "0..1" MemoryPrompt : asks
    User "1" -- "*" MissionProgress : tracks
    MissionProgress "*" -- "1" Mission : of
    User "1" -- "*" MemoryAnswer : writes
    MemoryAnswer "*" -- "1" MemoryPrompt : respondsTo
    MemoryAnswer "*" -- "0..1" FootprintEntry : attachedTo
    PhotoSpot "1" -- "*" FootprintEntry : capturedAt

    Trip "1" -- "0..*" Narrative : reviewedAs
    Narrative "*" --> "*" FootprintEntry : aggregates
    Narrative "*" --> "*" MemoryAnswer : quotes
    Narrative "1" -- "*" ShareArtifact : exportedAs

    CulturalGuide "1" -- "*" Spot : maintains
    CulturalGuide "1" -- "*" CulturalContext : maintains
    ContentAdmin "1" -- "*" Translation : reviews
    Translation "*" -- "1" Story : provides
```

> 列舉值說明
> - `Persona`: DEEP_DOCUMENTER (主要 — 深度記錄型) / CASUAL_TRAVELER (依行為而非國籍)
> - `TagType`: THEME (歷史/美食/建築/宗教) / SUBTHEME (小吃/甜點/熱門…)
> - `SpotCategory`: TEMPLE / HISTORICAL / COMMERCIAL / FOOD / ARCHITECTURE
> - `RouteType`: SAME_THEME / CROSS_ERA / CROSS_CATEGORY
> - `TransportMode`: WALK / BUS / TRAIN / MOUNTAIN_ROAD / SHUTTLE
> - `NarrativeStyle`: POSTCARD / TIMELINE / STORYBOOK
> - `ShareFormat`: LINK / IMAGE_9_16 / IMAGE_1_1 / SOCIAL_POST / MAP_ROUTE
> - `MemoryPromptStyle`: REFLECTIVE (如「若你生於那時代…」) / SENSORY (氣味/聲音記憶) / COMPARATIVE (與家鄉對比)
> - `Visibility`: PRIVATE / SHARED_WITH_NARRATIVE / PUBLIC

---

## 3. System Sequence Diagrams (SSD)

SSD 將整個系統視為單一黑盒,只描繪「外部 actor 與系統」之間的訊息往返,聚焦於每個 use case 的關鍵互動。

### 3.1 SSD-1:啟動 → 取得首條推薦路線

對應 UC2、UC13、UC5(NFR-06:3 步以內)。

```mermaid
sequenceDiagram
    autonumber
    actor T as 外國旅客
    participant S as :System

    T->>S: launchApp()
    S-->>T: requestPreferences()
    T->>S: setLanguageAndInterests(locale, themes[])
    S-->>T: showRecommendedRoutes(routes[])
    T->>S: selectRoute(routeId)
    S-->>T: confirmRouteAdded(tripId)
```

---

### 3.2 SSD-2:地圖式行程規劃

對應 UC4;FR-03、FR-04。

```mermaid
sequenceDiagram
    autonumber
    actor T as 外國旅客
    participant S as :System

    T->>S: createTrip()
    S-->>T: tripId
    loop 加入景點
        T->>S: addSpotToTrip(tripId, spotId, themeTags[])
        S-->>T: updatedTripWithColoredPath
    end
    T->>S: reorderSpots(tripId, orderedSpotIds[])
    S-->>T: routeRecalculatedWithTransfers
    T->>S: confirmTrip(tripId)
    S-->>T: tripFinalized
```

---

### 3.3 SSD-3:Heritage Mission 觸發與 Story Line 三件套

對應 UC3、UC6、UC7、UC8、UC19、UC20、UC21;FR-05、FR-06、FR-08、FR-10、FR-24、FR-25、FR-26。

```mermaid
sequenceDiagram
    autonumber
    actor T as 外國旅客
    participant S as :System

    T->>S: enterGeofence(spotId, userLoc)
    S-->>T: triggerMission(missionId, photoSpotCount, progress)
    T->>S: openPhotoSpot(photoSpotId)
    S-->>T: showStoryLine(suggestedShot, whySpecial, memoryPrompt)
    S-->>T: showShotOverlay(framing, composition)
    T->>S: capturePhoto(photoSpotId, photo)
    S-->>T: photoAttached
    alt 填寫 Memory Prompt(選填,鼓勵)
        T->>S: answerMemoryPrompt(promptId, body, visibility)
        S-->>T: memoryAnswerSaved
    else 跳過
        S-->>T: continueWithoutAnswer
    end
    S-->>T: updateMissionProgress(completed, total)
    opt 查看與下一景點脈絡
        T->>S: requestContext(spotId, nextSpotId)
        S-->>T: showCulturalDifferences(timeline, theme)
    end
    opt 移動中經過其他景點
        S-->>T: pushPassByStoryCard(spotName, shortStory)
    end
    T->>S: addFootprint(spotId, tags[], moodKeyword, photos[])
    S-->>T: footprintSaved
```

> Mission 不要求完整完成所有 photo spot — 即使 1/3 也視為造訪;但 FR-13 敘事生成會把已填寫的 MemoryAnswer 與 Mission 進度作為個人化素材的優先來源。

---

### 3.4 SSD-4:旅程回顧、敘事生成與策展分享

對應 UC9、UC10、UC11、UC12、UC22;FR-11、FR-12、FR-13、FR-14、FR-26、FR-27。

```mermaid
sequenceDiagram
    autonumber
    actor T as 外國旅客
    participant S as :System

    T->>S: openFootprintMap()
    alt 已累積足跡
        S-->>T: showClusteredFootprints(temple, commerce, history, missionProgress)
    else 尚無足跡(新用戶)
        S-->>T: showCuratedFootprintsFromOthers()
    end
    opt 補填 Memory Prompt / 補充照片
        T->>S: editFootprint(entryId, mood, photos[])
        T->>S: answerOrEditMemoryAnswer(promptId, body, visibility)
        S-->>T: footprintAndAnswerUpdated
    end
    T->>S: generateNarrative(tripId)
    Note over S: 引用 MemoryAnswer + Mission 進度 +<br/>足跡 + 文化脈絡
    S-->>T: narrativePreview(postcardStyle)
    T->>S: chooseCurationTemplate(style: POSTCARD | TIMELINE | STORYBOOK)
    S-->>T: templatePreviewWithSelectableContent
    T->>S: selectVisibleItems(photoIds[], memoryAnswerIds[])
    T->>S: chooseOutputFormat(IMAGE_9_16 | IMAGE_1_1 | LINK | MAP_ROUTE)
    T->>S: shareNarrative(format, audience)
    S-->>T: shareConfirmedWithPrivacyCheck
```

---

### 3.5 SSD-5:內容後台 — 維護景點與多語審核

對應 UC16、UC17、UC18;FR-18、NFR-01、NFR-02。

```mermaid
sequenceDiagram
    autonumber
    actor G as 導覽者
    actor A as 內容後台 Admin
    participant S as :System

    G->>S: createOrUpdateSpot(spotData, story)
    S-->>G: spotDraftSaved
    G->>S: linkCulturalContext(spotA, spotB, dimension)
    S-->>G: contextLinked
    G->>S: submitTranslation(storyId, locale, content)
    S-->>A: notifyPendingTranslation(storyId)
    A->>S: reviewTranslation(storyId, decision, notes)
    alt 通過
        S-->>G: translationApproved
        S-->>A: spotPublished
    else 退回
        S-->>G: translationRejected(notes)
    end
```

---

### 3.6 SSD-6:UGC 標籤協作與審核

對應 UC8 延伸 + FR-21。

```mermaid
sequenceDiagram
    autonumber
    actor T as 外國旅客
    actor A as 內容後台 Admin
    participant S as :System

    T->>S: suggestTag(spotId, proposedTag, parentTheme)
    S-->>T: tagSubmittedPendingReview
    S-->>A: notifyPendingUGCTag(suggestionId)
    A->>S: reviewUGCTag(suggestionId, decision)
    alt 通過
        S-->>T: tagAcceptedAndPublished
    else 拒絕
        S-->>T: tagRejected(reason)
    end
```

---

## 4. 圖表與需求對照表

| 圖表 | 對應 Use Case | 對應 Functional Requirement |
|---|---|---|
| Activity 1.1 啟動與興趣設定 | UC2, UC13 | FR-02, FR-09 |
| Activity 1.2 行程規劃 | UC4, UC5 | FR-03, FR-03a, FR-04, FR-19, FR-22 |
| Activity 1.3 現場探索與 Mission | UC3, UC6, UC7, UC8, UC19, UC20, UC21 | FR-05, FR-06, FR-07, FR-08, FR-10, FR-24, FR-25, FR-26 |
| Activity 1.4 回顧與策展分享 | UC9, UC10, UC11, UC12, UC22 | FR-11, FR-12, FR-13, FR-14, FR-26, FR-27 |
| Activity 1.5 內容維運 | UC16, UC17, UC18 | FR-18, FR-21, NFR-02 |
| Domain Class Diagram | 全部 | FR-02 ~ FR-27(資料模型支撐) |
| SSD-1 啟動 | UC2, UC13 | FR-02, NFR-06 |
| SSD-2 行程規劃 | UC4 | FR-03, FR-04 |
| SSD-3 Mission 觸發與三件套 | UC3, UC6, UC7, UC8, UC19, UC20, UC21 | FR-05, FR-06, FR-08, FR-10, FR-24, FR-25, FR-26 |
| SSD-4 回顧敘事與策展 | UC9, UC10, UC11, UC12, UC22 | FR-11, FR-12, FR-13, FR-14, FR-26, FR-27 |
| SSD-5 內容維運 | UC16, UC17, UC18 | FR-18, NFR-01, NFR-02 |
| SSD-6 UGC 標籤 | (UC8 延伸) | FR-21 |
