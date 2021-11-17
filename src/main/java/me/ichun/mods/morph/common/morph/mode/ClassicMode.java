package me.ichun.mods.morph.common.morph.mode;

import me.ichun.mods.morph.api.morph.MorphVariant;
import me.ichun.mods.morph.common.Morph;
import me.ichun.mods.morph.common.morph.MorphHandler;
import me.ichun.mods.morph.common.morph.save.PlayerMorphData;
import me.ichun.mods.morph.common.packet.PacketAcquisition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class ClassicMode implements MorphMode
{
    @Override
    public void handleMurderEvent(ServerPlayerEntity player, LivingEntity living)
    {
        if(canMorph(player))
        {
            MorphVariant variant = MorphHandler.INSTANCE.createVariant(living);
            if(canAcquireMorph(player, living, variant)) // we can morph to it
            {
                MorphHandler.INSTANCE.acquireMorph(player, variant);

                Morph.channel.sendTo(new PacketAcquisition(player.getEntityId(), living.getEntityId(), true), PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
            }
        }
    }

    @Override
    public boolean canMorph(PlayerEntity player)
    {
        return true;
    }

    @Override
    public boolean canAcquireMorph(PlayerEntity player, LivingEntity living, @Nullable MorphVariant variant) //variant should be the MorphVariant of the EntityLiving we're trying to acquire
    {
        if(variant == null)
        {
            return false;
        }

        PlayerMorphData playerMorphData = MorphHandler.INSTANCE.getPlayerMorphData(player);

        return !playerMorphData.containsVariant(variant);
    }

    @Override
    public int getMorphingDuration(PlayerEntity player)
    {
        return Morph.configServer.morphTime;
    }

    @Override
    public boolean hasUnlockedBiomass(PlayerEntity player)
    {
        return false;
    }

    @Override
    public boolean canAcquireBiomass(PlayerEntity player, LivingEntity living)
    {
        return false; // no biomass capabilities in classic.
    }

    @Override
    public double getBiomassAmount(PlayerEntity player, LivingEntity living)
    {
        return 0D; // no biomass capabilities in classic.
    }

    @Override
    public boolean isClassicMode()
    {
        return true;
    }
}