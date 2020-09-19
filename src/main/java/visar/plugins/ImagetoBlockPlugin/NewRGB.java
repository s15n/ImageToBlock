package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Material.*;

import java.awt.*;

public class NewRGB {
    static final Material[][][] materials = {
            { //RED
                    {
                        NETHERITE_BLOCK, REDSTONE_ORE, WHITE_TERRACOTTA, WHITE_TERRACOTTA
                    },
                    {
                        NETHER_BRICKS, RED_CONCRETE, PINK_TERRACOTTA, PINK_TERRACOTTA
                    },
                    {
                        NETHERRACK, RED_CONCRETE, RED_GLAZED_TERRACOTTA, RED_GLAZED_TERRACOTTA
                    },
                    {
                        RED_NETHER_BRICKS, NETHER_WART_BLOCK, RED_GLAZED_TERRACOTTA, RED_GLAZED_TERRACOTTA
                    }
            },
            { //ORANGE
                    {
                        SOUL_SAND, LIGHT_GRAY_TERRACOTTA, BIRCH_PLANKS, BIRCH_PLANKS
                    },
                    {
                        BROWN_TERRACOTTA, TERRACOTTA, YELLOW_GLAZED_TERRACOTTA, YELLOW_GLAZED_TERRACOTTA
                    },
                    {
                        BROWN_CONCRETE, ORANGE_TERRACOTTA, HONEYCOMB_BLOCK, HONEYCOMB_BLOCK
                    },
                    {
                        BROWN_CONCRETE, ORANGE_TERRACOTTA, ORANGE_CONCRETE, ORANGE_CONCRETE
                    }
            },
            { //YELLOW
                    {
                        NETHERITE_BLOCK, GOLD_ORE, SMOOTH_SANDSTONE, SMOOTH_SANDSTONE
                    },
                    {
                        GREEN_TERRACOTTA , LIME_TERRACOTTA , STRIPPED_BIRCH_WOOD, STRIPPED_BIRCH_WOOD
                    },
                    {
                        GREEN_TERRACOTTA, LIME_TERRACOTTA, GOLD_BLOCK, YELLOW_WOOL
                    },
                    {
                        GREEN_TERRACOTTA, WET_SPONGE, GOLD_BLOCK, YELLOW_WOOL
                    }
            },
            { //LIME
                    {
                        NETHERITE_BLOCK, MOSSY_COBBLESTONE, SLIME_BLOCK, SLIME_BLOCK
                    },
                    {
                        GREEN_CONCRETE, GREEN_GLAZED_TERRACOTTA, LIME_GLAZED_TERRACOTTA, LIME_GLAZED_TERRACOTTA
                    },
                    {
                        GREEN_CONCRETE, LIME_CONCRETE, LIME_WOOL, LIME_WOOL
                    },
                    {
                        GREEN_CONCRETE, LIME_CONCRETE, LIME_WOOL, LIME_WOOL
                    }
            },
            { //GREEN
                    {
                        NETHERITE_BLOCK, MOSSY_COBBLESTONE, LIME_GLAZED_TERRACOTTA, LIME_GLAZED_TERRACOTTA
                    },
                    {
                        GREEN_TERRACOTTA, MOSSY_COBBLESTONE, GREEN_GLAZED_TERRACOTTA, GREEN_GLAZED_TERRACOTTA
                    },
                    {
                        GREEN_TERRACOTTA, GREEN_WOOL, MELON, MELON
                    },
                    {
                            GREEN_TERRACOTTA, GREEN_WOOL, MELON, MELON
                    }
            },
            { //TEAL
                    {
                        GRAY_CONCRETE, EMERALD_ORE, LIGHT_GRAY_GLAZED_TERRACOTTA, LIGHT_GRAY_GLAZED_TERRACOTTA
                    },
                    {
                        DARK_PRISMARINE, EMERALD_ORE, PRISMARINE_BRICKS, PRISMARINE_BRICKS
                    },
                    {
                        DARK_PRISMARINE, PRISMARINE_BRICKS, EMERALD_BLOCK, EMERALD_BLOCK
                    },
                    {
                        DARK_PRISMARINE, PRISMARINE_BRICKS, EMERALD_BLOCK, EMERALD_BLOCK
                    }
            },
            { //CYAN
                    {
                        GRAY_CONCRETE, DIAMOND_ORE, WHITE_GLAZED_TERRACOTTA, WHITE_GLAZED_TERRACOTTA
                    },
                    {
                        WARPED_PLANKS, STRIPPED_WARPED_HYPHAE, DIAMOND_BLOCK, DIAMOND_BLOCK
                    },
                    {
                        CYAN_GLAZED_TERRACOTTA, CYAN_CONCRETE, STRIPPED_WARPED_HYPHAE, DIAMOND_BLOCK
                    },
                    {
                        CYAN_GLAZED_TERRACOTTA, CYAN_CONCRETE, CYAN_CONCRETE, DIAMOND_BLOCK
                    }
            },
            { //LIGHT BLUE
                    {
                        GRAY_CONCRETE, LAPIS_ORE, BLUE_ICE, PACKED_ICE
                    },
                    {
                        GRAY_CONCRETE, LAPIS_ORE, BLUE_ICE, PACKED_ICE
                    },
                    {
                        GRAY_CONCRETE, LAPIS_BLOCK, LIGHT_BLUE_CONCRETE, LIGHT_BLUE_CONCRETE
                    },
                    {
                        GRAY_CONCRETE, LAPIS_BLOCK, LIGHT_BLUE_CONCRETE, LIGHT_BLUE_CONCRETE
                    }
            },
            { //BLUE
                    {
                        OBSIDIAN, BLUE_CONCRETE, LIGHT_BLUE_TERRACOTTA, LIGHT_BLUE_TERRACOTTA
                    },
                    {
                        OBSIDIAN, BLUE_CONCRETE, BLUE_WOOL, BLUE_WOOL
                    },
                    {
                        OBSIDIAN, BLUE_CONCRETE, BLUE_WOOL, BLUE_WOOL
                    },
                    {
                        OBSIDIAN, BLUE_CONCRETE, BLUE_CONCRETE, BLUE_CONCRETE
                    }
            },
            { //PURPLE
                    {
                        CRYING_OBSIDIAN, BLUE_TERRACOTTA, BLUE_TERRACOTTA, BLUE_TERRACOTTA
                    },
                    {
                        CRYING_OBSIDIAN, PURPLE_CONCRETE, PURPLE_CONCRETE, PURPLE_CONCRETE
                    },
                    {
                        CRYING_OBSIDIAN, PURPLE_CONCRETE, PURPLE_CONCRETE, PURPLE_CONCRETE
                    },
                    {
                        CRYING_OBSIDIAN, PURPLE_CONCRETE, PURPLE_CONCRETE, PURPLE_CONCRETE
                    }
            },
            { //MAGENTA
                    {
                        BLACKSTONE, MAGENTA_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA
                    },
                    {
                        BLACKSTONE, MAGENTA_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA
                    },
                    {
                        BLACKSTONE, MAGENTA_CONCRETE, MAGENTA_WOOL, MAGENTA_WOOL
                    },
                    {
                        BLACKSTONE, MAGENTA_CONCRETE, MAGENTA_WOOL, MAGENTA_WOOL
                    }
            },
            { //PINK
                    {
                        BLACKSTONE, PINK_TERRACOTTA, PINK_GLAZED_TERRACOTTA, PINK_GLAZED_TERRACOTTA
                    },
                    {
                        CRIMSON_PLANKS, STRIPPED_CRIMSON_HYPHAE, PINK_CONCRETE, PINK_CONCRETE
                    },
                    {
                        CRIMSON_PLANKS, STRIPPED_CRIMSON_HYPHAE, PINK_CONCRETE, PINK_CONCRETE
                    },
                    {
                        CRIMSON_PLANKS, STRIPPED_CRIMSON_HYPHAE, PINK_CONCRETE, PINK_CONCRETE
                    },
            }
    };
    static final Material[] zeroSat = {NETHERITE_BLOCK, STONE, IRON_BLOCK, WHITE_CONCRETE};
    static final Material nullMat = AIR;

    @NotNull
    public static Material getClosestBlockValue(Color c) {
        float[] hsb = Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
        int hue = (int) (hsb[0]*12+0.5f);
        int saturation = (int) (hsb[1]*4+0.5f);
        int brightness = (int) (hsb[2]*4+0.5f);
        if (brightness==0) {
            return BLACK_CONCRETE;
        }
        if (saturation==0) {
            return zeroSat[brightness-1];
        }
        if (hue == 12) {
            hue = 0;
        }
        Material r = materials[hue][saturation-1][brightness-1];
        return r==null?nullMat:r;
    }
}
