package net.xalcon.chococraft.common;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.chococraft.common.entities.EntityChocobo;
import net.xalcon.chococraft.common.entities.properties.EntityDataSerializers;
import net.xalcon.chococraft.common.network.PacketManager;
import net.xalcon.chococraft.common.world.worldgen.WorldGenGysahlGreen;

public class CommonProxy
{
    @SuppressWarnings({"EmptyMethod", "unused"})
    public void onPreInit(FMLPreInitializationEvent event)
    {
    }

    @SuppressWarnings("unused")
    public void onInit(FMLInitializationEvent event)
    {
        EntityDataSerializers.init();
        PacketManager.init();

        GameRegistry.registerWorldGenerator(new WorldGenGysahlGreen(), ChocoConfig.world.gysahlGreenSpawnWeight);

        EntityRegistry.addSpawn(EntityChocobo.class,
                ChocoConfig.world.chocoboSpawnWeight,
                ChocoConfig.world.chocoboPackSizeMin,
                ChocoConfig.world.chocoboPackSizeMax,
                EnumCreatureType.CREATURE,
                BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS).toArray(new Biome[0]));
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    public void onPostInit(FMLPostInitializationEvent event)
    {
    }
}
