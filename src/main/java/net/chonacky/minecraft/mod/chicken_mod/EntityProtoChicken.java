package net.chonacky.minecraft.mod.chicken_mod;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityProtoChicken extends ChickenEntity
{

	public EntityProtoChicken(EntityType<? extends ChickenEntity> type, World worldIn) {
		super(type, worldIn);
		//this.setSize(2.5F, 5.0F);
		this.setBoundingBox(this.getBoundingBox().offset(0, 0, 25));
		this.stepHeight = 2.1F;
		
		//these appear to be no longer relevant in 1.13
//		this.jumpPower = 0.0F;
//		this.isJumping = false;
	}
	
	@Override
	public float getStandingEyeHeight(Pose pose, EntitySize size) {
		return super.getEyeHeight()-0.5F;
	}
	
	@Override
	public EntityType<?> getType()
	{
		return ChickenMod.RegistryEvents.protochicken;
	}
	
	@Override
	public void livingTick()
	    {
	        super.livingTick();
	        if (this.world.isRemote && this.dataManager.isDirty())  {
	            this.dataManager.setClean();
	        }
	    }
	
	@Override 
    public boolean processInteract(PlayerEntity player, Hand hand)
	    {
//		if (IsInstanceOf(player,EntityPlayer.class))
		 this.mountTo(player);
		 super.processInteract(player, hand);
		 return true;
	    }
	
	 
	protected void mountTo(PlayerEntity player)
	    {
	        player.rotationYaw = this.rotationYaw;
	        player.rotationPitch = this.rotationPitch;
	        if (!this.world.isRemote)  player.startRiding(this);
	    }

	@Override
	 public void updatePassenger(Entity passenger)
	    {
	        super.updatePassenger(passenger);
	        float f = MathHelper.sin(this.renderYawOffset * 0.017453292F);
	        float f1 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
//unused	float f2 = 0.1F;
//unused	float f3 = 0.0F;
	        passenger.setPosition(
	        		this.posX + (double)(0.1F * f),
	        		this.posY + (double)(this.getHeight() * 0.35F) + passenger.getYOffset() + 0.0D,
	        		this.posZ - (double)(0.1F * f1)													);
	        if (passenger instanceof LivingEntity)	((LivingEntity)passenger).renderYawOffset = this.renderYawOffset;
	    }

	 

	@Override
	public void travel(Vec3d vec3d)
	    {	
	        if (this.isBeingRidden() && this.canBeSteered() )	{
	        										//&& this.isHorseSaddled()
	            LivingEntity controllingPassenger = (LivingEntity)this.getControllingPassenger();
	            this.rotationYaw = controllingPassenger.rotationYaw;
	            this.prevRotationYaw = this.rotationYaw;
	            this.rotationPitch = controllingPassenger.rotationPitch * 0.5F;
	            this.setRotation(this.rotationYaw, this.rotationPitch);
	            this.renderYawOffset = this.rotationYaw;
	            this.rotationYawHead = this.renderYawOffset;
	            vec3d = new Vec3d(controllingPassenger.moveStrafing * 0.5F,vec3d.y,controllingPassenger.moveForward);
	            if (vec3d.z <= 0.0F) vec3d = new Vec3d(vec3d.x,vec3d.y,vec3d.z * 0.25F); 
	            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
	            if (this.canPassengerSteer()) {
	                this.setAIMoveSpeed((float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
	                super.travel(vec3d);
	            }
	            else if (controllingPassenger instanceof PlayerEntity)  {
	                this.setMotion(Vec3d.ZERO);
	            }
	            if (this.onGround)	 this.isJumping = false;
	        }
	        else {
	            this.jumpMovementFactor = 0.02F;
	            super.travel(vec3d);
	        }   
	    }

	@Override    
	public boolean canBeSteered()  
		{
	        return this.getControllingPassenger() instanceof LivingEntity;
	    }    
	    
	@Override @Nullable
    public Entity getControllingPassenger()
	    {
	        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
	    }

}
