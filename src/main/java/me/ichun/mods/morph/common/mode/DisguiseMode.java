package me.ichun.mods.morph.common.mode;

import me.ichun.mods.morph.api.mob.MobData;
import me.ichun.mods.morph.api.mob.trait.Trait;
import me.ichun.mods.morph.api.mob.trait.ability.Ability;
import me.ichun.mods.morph.api.morph.MorphVariant;
import me.ichun.mods.morph.common.Morph;
import me.ichun.mods.morph.common.mob.MobDataHandler;
import me.ichun.mods.morph.common.morph.MorphHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class DisguiseMode implements MorphMode
{
    @Override
    public void handleMurderEvent(ServerPlayerEntity player, LivingEntity living)
    {
        MorphVariant variant = MorphHandler.INSTANCE.createVariant(living);

        MorphHandler.INSTANCE.morphTo(player, variant);
    }

    @Override
    public boolean canMorph(PlayerEntity player)
    {
        return false;
    }

    @Override
    public boolean canAcquireMorph(PlayerEntity player, LivingEntity living, @Nullable MorphVariant variant)
    {
        return false;
    }

    @Override
    public int getMorphingDuration(PlayerEntity player)
    {
        return Morph.configServer.morphTime;
    }

    @Override
    public ArrayList<Trait<?>> getTraitsForVariant(PlayerEntity player, MorphVariant variant)
    {
        ArrayList<Trait<?>> traits = new ArrayList<>();

        MobData mobData = MobDataHandler.getMobData(variant.id);

        if(mobData != null && mobData.traits != null)
        {
            for(Trait<?> trait : mobData.traits)
            {
                if(trait != null && !Morph.configServer.disabledTraits.contains(trait.type) && trait.upgradeFor == null) //no trait upgrades in classic
                {
                    traits.add(trait.copy());
                }
            }

            for(Trait<?> trait : traits)
            {
                trait.player = player;
                trait.stateTraits = traits;
            }
        }

        return traits;
    }

    @Override
    public boolean canUseAbility(PlayerEntity player, Ability<?> ability)
    {
        return true;
    }

    @Override
    public boolean hasUnlockedBiomass(PlayerEntity player)
    {
        return false;
    }

    @Override
    public boolean canAcquireBiomass(PlayerEntity player, LivingEntity living)
    {
        return false;
    }

    @Override
    public double getBiomassAmount(PlayerEntity player, LivingEntity living)
    {
        return 0;
    }

    @Override
    public String getModeName()
    {
        return "disguise";
    }
}