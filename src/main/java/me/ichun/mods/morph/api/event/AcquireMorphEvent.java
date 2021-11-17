package me.ichun.mods.morph.api.event;

import me.ichun.mods.morph.api.morph.MorphVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class AcquireMorphEvent extends PlayerEvent
{
    private final MorphVariant variant;
    public AcquireMorphEvent(PlayerEntity player, MorphVariant variant)
    {
        super(player);
        this.variant = variant;
    }

    public MorphVariant getVariant()
    {
        return variant;
    }
}