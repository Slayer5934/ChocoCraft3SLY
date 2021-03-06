package net.chococraft.common.entities.breeding;

import net.chococraft.common.entities.properties.ChocoboColor;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.EntityChocobo;
import net.chococraft.common.entities.properties.ChocoboAttributes;

@SuppressWarnings("WeakerAccess")
public class ChocoboStatSnapshot {
    public static final ChocoboStatSnapshot DEFAULT;
    public static final String NBTKEY_GENERATION = "Generation";
    public static final String NBTKEY_HEALTH = "Health";
    public static final String NBTKEY_SPEED = "Speed";
    public static final String NBTKEY_STAMINA = "Stamina";
    public static final String NBTKEY_CAN_SPRINT = "CanSprint";
    public static final String NBTKEY_CAN_GLIDE = "CanGlide";
    public static final String NBTKEY_CAN_DIVE = "CanDive";
    public static final String NBTKEY_CAN_FLY = "CanFly";
    public static final String NBTKEY_COLOR = "Color";

    public int generation;
    public float health;
    public float speed;
    public float stamina;
    public boolean canSprint;
    public boolean canGlide;
    public boolean canDive;
    public boolean canFly;
    public ChocoboColor color;

    static {
        DEFAULT = new ChocoboStatSnapshot();
        DEFAULT.generation = 1;
        DEFAULT.health = ChocoConfig.chocobo.defaultHealth;
        DEFAULT.stamina = ChocoConfig.chocobo.defaultStamina;
        DEFAULT.speed = ChocoConfig.chocobo.defaultSpeed / 100f;

        DEFAULT.canSprint = false;
        DEFAULT.canGlide = false;
        DEFAULT.canDive = false;
        DEFAULT.canFly = false;
        DEFAULT.color = ChocoboColor.YELLOW;
    }

    private ChocoboStatSnapshot() {
    }

    public ChocoboStatSnapshot(EntityChocobo chocobo) {
        this.generation = chocobo.getGeneration();
        this.health = (float) chocobo.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
        this.speed = (float) chocobo.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
        this.stamina = (float) chocobo.getEntityAttribute(ChocoboAttributes.MAX_STAMINA).getBaseValue();

        this.canSprint = chocobo.canSprint();
        this.canGlide = chocobo.canGlide();
        this.canDive = chocobo.canDive();
        this.canFly = chocobo.canFly();
        this.color = chocobo.getChocoboColor();
    }

    public ChocoboStatSnapshot(NBTTagCompound nbt) {
        this.generation = nbt.getInteger(NBTKEY_GENERATION);
        this.health = nbt.getFloat(NBTKEY_HEALTH);
        this.speed = nbt.getFloat(NBTKEY_SPEED);
        this.stamina = nbt.getFloat(NBTKEY_STAMINA);

        this.canSprint = nbt.getBoolean(NBTKEY_CAN_SPRINT);
        this.canGlide = nbt.getBoolean(NBTKEY_CAN_GLIDE);
        this.canDive = nbt.getBoolean(NBTKEY_CAN_DIVE);
        this.canFly = nbt.getBoolean(NBTKEY_CAN_FLY);
        this.color = ChocoboColor.values()[nbt.getByte(NBTKEY_COLOR)];
    }

    public NBTTagCompound serialize() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger(NBTKEY_GENERATION, this.generation);
        nbt.setFloat(NBTKEY_HEALTH, this.health);
        nbt.setFloat(NBTKEY_SPEED, this.speed);
        nbt.setFloat(NBTKEY_STAMINA, this.stamina);

        nbt.setBoolean(NBTKEY_CAN_FLY, this.canFly);
        nbt.setBoolean(NBTKEY_CAN_GLIDE, this.canGlide);
        nbt.setBoolean(NBTKEY_CAN_SPRINT, this.canSprint);
        nbt.setBoolean(NBTKEY_CAN_DIVE, this.canDive);
        nbt.setByte(NBTKEY_COLOR, (byte) this.color.ordinal());
        return nbt;
    }
}
