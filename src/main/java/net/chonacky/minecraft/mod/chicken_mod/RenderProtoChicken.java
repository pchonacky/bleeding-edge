package net.chonacky.minecraft.mod.chicken_mod;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class RenderProtoChicken extends MobRenderer<EntityProtoChicken, ModelProtoChicken<EntityProtoChicken>>
{

	 private static final ResourceLocation PROTOCHICKEN_TEXTURES = new ResourceLocation(ChickenMod.MODID,"textures/entity/byhut.png");
    																	//"chicken:textures/entity/checker.png"
	 
	 // public RenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn)
    public RenderProtoChicken(EntityRendererManager manager)
    {
    	super(manager, new ModelProtoChicken<EntityProtoChicken>(), 1.75F);
    }
 
    //@Override
	protected ResourceLocation getEntityTexture(EntityProtoChicken entity) {
		return PROTOCHICKEN_TEXTURES;
	}

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float handleRotationFloat(EntityProtoChicken livingBase, float partialTicks)
    {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    //probably not needed (implemented in Super class)
    /*
    @Override
    public void doRender(EntityProtoChicken entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (entity.isInWater()) { super.doRender(entity, x, (y-1.0D), z, entityYaw, partialTicks); }
        else { super.doRender(entity, x, y, z, entityYaw, partialTicks); }
        if (!this.renderOutlines) { this.renderLeash(entity, x, y, z, entityYaw, partialTicks);   }
    }
    */
    
	
}