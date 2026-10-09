package net.zhaiji.whimsy_delight.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.menu.CraftingStationMenu;

public class WDMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, WhimsyDelight.MOD_ID);

    public static final RegistryObject<MenuType<CraftingStationMenu>> CRAFTING_STATION = MENU_TYPES.register(
        "crafting_station",
        () -> IForgeMenuType.create(CraftingStationMenu::new)
    );
}
