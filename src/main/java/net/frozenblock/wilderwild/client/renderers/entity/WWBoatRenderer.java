package net.frozenblock.wilderwild.client.renderers.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entities.WWBoat;
import net.frozenblock.wilderwild.init.WWModelLayers;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class WWBoatRenderer extends EntityRenderer<WWBoat> {
    private final Map<WWBoat.WWBoatType, Pair<ResourceLocation, BoatModel>> texturesAndModels;

    public WWBoatRenderer(EntityRendererProvider.Context ctx, boolean chest) {
        super(ctx);
        this.shadowRadius = 0.8f;
        this.texturesAndModels = Stream.of(WWBoat.WWBoatType.values()).collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(new ResourceLocation(WilderWild.MOD_ID, WWBoatRenderer.getTexture(type, chest)), this.createModel(ctx, type, chest))));
    }

    private BoatModel createModel(EntityRendererProvider.Context ctx, WWBoat.WWBoatType type, boolean chest) {
        ModelLayerLocation entityModelLayer = chest ? WWModelLayers.createChestBoat(type) : WWModelLayers.createBoat(type);
        return new BoatModel(ctx.bakeLayer(entityModelLayer), chest);
    }

    private static String getTexture(WWBoat.WWBoatType type, boolean chest) {
        if (chest) {
            return "textures/entity/chest_boat/" + type.getName() + ".png";
        }
        return "textures/entity/boat/" + type.getName() + ".png";
    }

    @Override
    public void render(WWBoat boatEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        float h = (float)boatEntity.getHurtTime() - g;
        float j = boatEntity.getDamage() - g;
        if (j < 0.0f) {
            j = 0.0f;
        }
        if (h > 0.0f) {
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * (float)boatEntity.getHurtDir()));
        }
        if (!Mth.equal(boatEntity.getBubbleAngle(g), 0.0f)) {
            matrixStack.mulPose(new Quaternion(new Vector3f(1.0f, 0.0f, 1.0f), boatEntity.getBubbleAngle(g), true));
        }
        Pair<ResourceLocation, BoatModel> pair = this.texturesAndModels.get(boatEntity.getWWBoatType());
        ResourceLocation identifier = pair.getFirst();
        BoatModel boatEntityModel = pair.getSecond();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0f));
        boatEntityModel.setupAnim(boatEntity, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(boatEntityModel.renderType(identifier));
        boatEntityModel.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!boatEntity.isUnderWater()) {
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderType.waterMask());
            boatEntityModel.waterPatch().render(matrixStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY);
        }
        matrixStack.popPose();
        super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(WWBoat boatEntity) {
        return this.texturesAndModels.get(boatEntity.getWWBoatType()).getFirst();
    }

}
