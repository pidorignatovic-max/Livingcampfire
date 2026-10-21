# Living Campfires

A Fabric mod scaffold for Minecraft Java 1.21.4, Fabric API 0.119.4 and Java 21.

## Current implementation
- Seven custom campfire block variants based on vanilla `CampfireBlock`.
- Seven portable campfire items.
- Warm: regeneration; Herbal Speed: speed; Herbal Defense: resistance.
- Herbal Utility: jump boost; Herbal Mystic: luck and night vision.
- Frost: slowness; Signal: glowing and extra smoke.
- Portable items apply effects while held in either hand and lose durability every 40 ticks.

## Build
Requires JDK 21 and internet access for Gradle dependencies. A Gradle wrapper and original textures are not included in this recovery archive. Run `gradle build`; output should be in `build/libs/`.

## Status
This is a reconstruction and has not been verified by a successful Gradle build or in-game test. Effects beyond Warm and Signal are provisional. Test cooking, extinguishing, relighting, block drops, multiplayer and effect balance before release.
