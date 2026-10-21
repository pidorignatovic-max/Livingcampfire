package com.crosskai.livingcampfires;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LivingCampfires implements ModInitializer {
    public static final String MOD_ID = "livingcampfires";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item WARM_CAMPFIRE = registerItem("warm_campfire", new Item(new Item.Settings()));
    public static final Item HERBAL_SPEED_CAMPFIRE = registerItem("herbal_speed_campfire", new Item(new Item.Settings()));
    public static final Item HERBAL_DEFENSE_CAMPFIRE = registerItem("herbal_defense_campfire", new Item(new Item.Settings()));
    public static final Item HERBAL_UTILITY_CAMPFIRE = registerItem("herbal_utility_campfire", new Item(new Item.Settings()));
    public static final Item HERBAL_MYSTIC_CAMPFIRE = registerItem("herbal_mystic_campfire", new Item(new Item.Settings()));
    public static final Item FROST_CAMPFIRE = registerItem("frost_campfire", new Item(new Item.Settings()));
    public static final Item SIGNAL_CAMPFIRE = registerItem("signal_campfire", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(WARM_CAMPFIRE);
            entries.add(HERBAL_SPEED_CAMPFIRE);
            entries.add(HERBAL_DEFENSE_CAMPFIRE);
            entries.add(HERBAL_UTILITY_CAMPFIRE);
            entries.add(HERBAL_MYSTIC_CAMPFIRE);
            entries.add(FROST_CAMPFIRE);
            entries.add(SIGNAL_CAMPFIRE);
        });
        LOGGER.info("Living Campfires initialized (initial scaffold).");
    }
}
