package MISSmyIDmods.compactedstuff.block.entity;

import MISSmyIDmods.compactedstuff.block.CompactedBlocks;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.EntityFallingSand;
import net.minecraft.core.world.World;

public class EntityDoubleCompactedGravel extends EntityFallingSand {
	public int fuse;
	public int blockID;
	public EntityDoubleCompactedGravel(World world) {
		super(world);
		this.fuse = 0;
		this.blockID = CompactedBlocks.compactedSand.id;
		this.blocksBuilding = true;
		this.setSize(0.98F, 0.98F);
		this.heightOffset = this.bbHeight / 2.0F;
	}
	protected void init() {
	}

	public String getEntityTexture() {
		return "/assets/compactedstuff/block/DoublePacketGravel.png";
	}

	protected boolean makeStepSound() {
		return false;
	}

	public boolean isPickable() {
		return !this.removed;
	}

	public void tick() {
		this.blockID = CompactedBlocks.compactedSand.id;
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.yd -= 0.03999999910593033;
		this.move(this.xd, this.yd, this.zd);
		this.xd *= 0.9800000190734863;
		this.yd *= 0.9800000190734863;
		this.zd *= 0.9800000190734863;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
			this.yd *= -0.5;
		}

		if (this.fuse-- <= 0) {
			if (!this.world.isClientSide) {
				this.world.newExplosion(this, this.x, this.y, this.z, 1.5F, false, true);
				this.remove();
			} else {
				this.remove();
			}
		} else {
			this.world.spawnParticle("smoke", this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0);
		}

	}

	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putByte("Fuse", (byte)this.fuse);
		tag.putShort("Tile", (short)this.blockID);
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		this.fuse = tag.getByte("Fuse");
		this.blockID = tag.getShort("Tile") & 16383;
	}

	public float getShadowHeightOffs() {
		return 0.0F;
	}
}
