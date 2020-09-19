package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Material.*;

import java.awt.*;

public class NewRGB {
    static final Material[][][] materials = {
            { //RED
                    {
                            null, REDSTONE_ORE, WHITE_TERRACOTTA, null
                    },
                    {
                        NETHER_BRICKS, PINK_TERRACOTTA, null, null
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
                            null, GOLD_ORE, SMOOTH_SANDSTONE, SMOOTH_SANDSTONE
                    },
                    {
                        GREEN_TERRACOTTA /*WTF*/, LIME_TERRACOTTA /*?*/, WET_SPONGE, STRIPPED_BIRCH_WOOD
                    },
                    {
                            null, null, GOLD_BLOCK, YELLOW_WOOL
                    },
                    {
                            null, null, GOLD_BLOCK, YELLOW_WOOL
                    }
            },
            { //LIME
                    {
                            null, MOSSY_COBBLESTONE, SLIME_BLOCK, SLIME_BLOCK
                    },
                    {
                        GREEN_CONCRETE, GREEN_GLAZED_TERRACOTTA, null, null
                    },
                    {
                            null, LIME_CONCRETE, LIME_WOOL, LIME_WOOL
                    },
                    {
                            null, LIME_CONCRETE, LIME_WOOL, LIME_WOOL
                    }
            },
            { //GREEN
                    {
                            null, null, null, null
                    },
                    {
                            null, null, null, null
                    },
                    {
                            null, null, null, null
                    },
                    {
                            null, null, null, null
                    },
            },
            { //TEAL
                    {
                            null, EMERALD_ORE, null, null
                    },
                    {
                        DARK_PRISMARINE, null, /*SEA_LANTERN*/ null, null
                    },
                    {
                            null, null, EMERALD_BLOCK, EMERALD_BLOCK
                    },
                    {
                            null, null, EMERALD_BLOCK, EMERALD_BLOCK
                    }
            },
            { //CYAN
                    {
                            null, DIAMOND_ORE, WHITE_GLAZED_TERRACOTTA, WHITE_GLAZED_TERRACOTTA
                    },
                    {
                        WARPED_PLANKS, STRIPPED_WARPED_HYPHAE, DIAMOND_BLOCK, DIAMOND_BLOCK
                    },
                    {
                            null,CYAN_CONCRETE, null /*TODO*/, DIAMOND_BLOCK
                    },
                    {
                            null, CYAN_CONCRETE, CYAN_CONCRETE, DIAMOND_BLOCK
                    }
            },
            { //LIGHT BLUE
                    {
                        GRAY_CONCRETE, null, null, /*ICE*/null
                    },
                    {
                            null, null, BLUE_ICE, BLUE_ICE
                    },
                    {
                            null, LAPIS_BLOCK, LIGHT_BLUE_CONCRETE, LIGHT_BLUE_CONCRETE
                    },
                    {
                            null, LAPIS_BLOCK, LIGHT_BLUE_CONCRETE, LIGHT_BLUE_CONCRETE
                    }
            },
            { //BLUE
                    {
                            null, LIGHT_BLUE_TERRACOTTA, null, null
                    },
                    {
                            null, null, null, null
                    },
                    {
                            null, BLUE_CONCRETE, BLUE_CONCRETE, BLUE_CONCRETE
                    },
                    {
                            null, BLUE_CONCRETE, BLUE_CONCRETE, BLUE_CONCRETE
                    }
            },
            { //PURPLE
                    {
                        BLUE_TERRACOTTA, null, null, null
                    },
                    {
                        null, PURPLE_CONCRETE, PURPLE_CONCRETE, PURPLE_CONCRETE
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
                        BLACKSTONE, MAGENTA_TERRACOTTA, null, null
                    },
                    {
                        null, null, MAGENTA_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA
                    },
                    {
                        null, MAGENTA_CONCRETE, MAGENTA_WOOL, MAGENTA_WOOL
                    },
                    {
                        null, MAGENTA_CONCRETE, MAGENTA_WOOL, MAGENTA_WOOL
                    }
            },
            { //PINK
                    {
                        null, null, PINK_GLAZED_TERRACOTTA, PINK_GLAZED_TERRACOTTA
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
