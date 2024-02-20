package com.mds.ruberia.util;

import com.mds.ruberia.Ruberia;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks{

        public static final TagKey<Block> VALUABLE_ORES = createTag("valuable_ores");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(Ruberia.MOD_ID,name));
        }

    }

    public static class Items{

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(Ruberia.MOD_ID,name));
        }

    }
}
