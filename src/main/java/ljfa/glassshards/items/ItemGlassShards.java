package ljfa.glassshards.items;

import java.util.List;

import ljfa.glassshards.Reference;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Multimap;

/*
 * Metadata:
 * 0 - 15: colored shards
 * 16    : clear shards
 */
public class ItemGlassShards extends Item implements IModeledItem {
    private final String[] variants;
    
    ItemGlassShards() {
        variants = new String[17];
        for(int i = 0; i < 16; i++)
            variants[i] = Reference.MODID + ":glass_shards_" + colorNames[i];
        variants[16] = Reference.MODID + ":glass_shards";
        
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
        ModItems.register(this, "glass_shards");
    }
    
    public String getVariant(int meta) {
        return variants[MathHelper.clamp_int(meta, 0, 16)];
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String prefix = "item." + Reference.MODID + ":glass_shards";
        int meta = stack.getItemDamage();
        if(meta < 16)
            return prefix + "." + colorNames[meta];
        else
            return prefix;
    }
    
    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        double damage = 1.0;
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(itemModifierUUID, "Weapon modifier", damage, 0));
        return multimap;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        //List clear shards first
        list.add(new ItemStack(item, 1, 16));
        for(int i = 0; i < 16; i++)
            list.add(new ItemStack(item, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(ItemModelMesher mesher) {
        ModelBakery.addVariantName(this, variants);
        mesher.register(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(getVariant(stack.getItemDamage()), "inventory");
            }
        });
    }
    
    public static final String[] colorNames = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
        "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
}
