# GT15 Core / 绫华工业核心

GT15 LightTech Sky 专用 Forge 1.20.1 联动模组，用于优化 GregTech CEu Modern 与 AE2 的繁琐操作。

## Target

- Minecraft: 1.20.1
- Loader: Forge 47.x
- Java: 17
- Main pack: GT15 LightTech Sky
- Owner: wq154

## Features

- GT 工具减负：维护工具包、IO 蓝图、批量零件卡
- AE2 样板优化：样板倍率卡、样板路由节点
- GT-AE 桥接：订单桥卡、订单缓存银行、订单超算任务锚点
- 后期大型多方块控制器：ZPM 到 MAX 的结构检测控制器
- 性能导向：轻量诊断、惰性扫描、避免实体物品海

## Repository

https://github.com/wq154/GT15-Core

## Build

```bat
REM Use Java 17
set JAVA_HOME=C:\Program Files\Zulu\zulu-17
set PATH=%JAVA_HOME%\bin;%PATH%

REM If Gradle is installed
gradle build
```

如果没有本机 Gradle，后续建议补 Gradle Wrapper：

```bat
gradle wrapper --gradle-version 8.8
.\gradlew.bat build
```

## Notes

本模组是 GT15 LightTech Sky 的包专用自创模组，但从现在开始单独维护，方便后续独立修改、打包和发布。
