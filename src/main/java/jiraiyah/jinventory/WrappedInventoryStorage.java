/***********************************************************************************
 * Copyright (c) 2024 Alireza Khodakarami (Jiraiyah)                               *
 * ------------------------------------------------------------------------------- *
 * MIT License                                                                     *
 * =============================================================================== *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 * ------------------------------------------------------------------------------- *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 * ------------------------------------------------------------------------------- *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 ***********************************************************************************/

package jiraiyah.jinventory;

import jiraiyah.jiralib.PosHelper;
import jiraiyah.jiralib.blockentity.NoScreenUpdatableBE;
import jiraiyah.jiralib.interfaces.NBTSerializable;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A wrapper for multiple, sided, inventories that implements the {@link NBTSerializable} interface.
 *
 * @author TurtyWurty
 */
@SuppressWarnings("unused")
public class WrappedInventoryStorage implements NBTSerializable<NbtList>
{
    /**
     * A list of inventories wrapped by this storage. Each inventory is an instance of {@link SimpleInventory}.
     * This list is used to manage multiple inventories collectively.
     */
    private final List<SimpleInventory> inventories = new ArrayList<>();

    /**
     * A list of {@link InventoryStorage} instances corresponding to each inventory in the {@code inventories} list.
     * This list is used to handle the storage operations for each inventory.
     */
    private final List<InventoryStorage> storages = new ArrayList<>();

    /**
     * A map that associates each {@link Direction} with an {@link InventoryStorage}.
     * This map is used to manage inventories that are accessible from specific directions.
     */
    private final Map<Direction, InventoryStorage> sidedStorageMap = new HashMap<>();

    /**
     * A {@link CombinedStorage} that aggregates all {@link InventoryStorage} instances in the {@code storages} list.
     * This combined storage allows for unified operations across all wrapped inventories.
     */
    private final CombinedStorage<ItemVariant, InventoryStorage> combinedStorage = new CombinedStorage<>(this.storages);

    /**
     * Creates a new WrappedInventoryStorage with the given inventories.
     * The inventory will be assigned to no directions because we didn't set a specific direction for the inventory.
     *
     * @param inventory the inventory to wrap
     */
    public void addInventory(@NotNull SimpleInventory inventory)
    {
        addInventory(inventory, null);
    }

    /**
     * Creates a new WrappedInventoryStorage with the given inventory and direction.
     *
     * @param inventory the inventory to wrap
     * @param direction the direction to assign the inventory to. If null, the inventory will be assigned to all directions.
     */
    public void addInventory(@NotNull SimpleInventory inventory, Direction direction)
    {
        this.inventories.add(inventory);
        InventoryStorage storage = InventoryStorage.of(inventory, direction);
        this.storages.add(storage);
        this.sidedStorageMap.put(direction, storage);
    }

    /**
     * Creates a new WrappedInventoryStorage with a new sync inventory of the given size.
     * The inventory will be assigned to no directions because we didn't set a specific direction for the inventory.
     *
     * @param blockEntity the block entity to which the inventory will be added.
     * @param size the size of the sync inventory storage.
     */
    public void addSyncInventory(NoScreenUpdatableBE blockEntity, int size)
    {
        addInventory(new SyncingSimpleInventory(blockEntity, size));
    }

    /**
     * Creates a new WrappedInventoryStorage with a new sync inventory of the given size and direction.
     *
     * @param blockEntity the block entity to which the inventory will be added.
     * @param size the size of the sync inventory storage.
     * @param direction the direction associated with this inventory storage.
     */
    public void addSyncInventory(NoScreenUpdatableBE blockEntity, int size, Direction direction)
    {
        addInventory(new SyncingSimpleInventory(blockEntity, size), direction);
    }

    /**
     * Retrieves the list of inventories wrapped by this storage. Each inventory in the list is an instance of {@link SimpleInventory}.
     * This method provides access to the underlying inventories, allowing for direct interaction with each inventory's contents.
     *
     * @return a list of inventories, where each inventory is an instance of {@link SimpleInventory}.
     */
    public List<SimpleInventory> getInventories()
    {
        return inventories;
    }

    /**
     * Retrieves the list of {@link InventoryStorage} instances corresponding to each inventory in the {@code inventories} list.
     * This list is used to handle the storage operations for each inventory, allowing for individual management of each
     * inventory's storage capabilities.
     *
     * @return a list of {@link InventoryStorage} instances, each representing the storage operations for a corresponding inventory.
     */
    public List<InventoryStorage> getStorages()
    {
        return storages;
    }

    /**
     * Retrieves the map that associates each {@link Direction} with its corresponding {@link InventoryStorage}.
     * This map is used to manage and access inventories that are accessible from specific directions, allowing
     * for direction-based inventory operations.
     *
     * @return a map where each key is a {@link Direction} and each value is the corresponding {@link InventoryStorage}.
     */
    public Map<Direction, InventoryStorage> getSidedStorageMap()
    {
        return sidedStorageMap;
    }

    /**
     * Retrieves the combined storage that aggregates all {@link InventoryStorage} instances in this wrapper.
     * This combined storage allows for unified operations across all wrapped inventories, providing a single
     * interface to interact with multiple inventories as if they were one.
     *
     * @return the {@link CombinedStorage} instance that represents the aggregated storage of all wrapped inventories.
     */
    public CombinedStorage<ItemVariant, InventoryStorage> getCombinedStorage()
    {
        return combinedStorage;
    }

    /**
     * Retrieves the storage slot at the specified index for the given direction.
     *
     * @param slot      the index of the slot to retrieve.
     * @param direction the direction from which to access the storage.
     * @return the {@link SingleSlotStorage} at the specified index and direction, or null if no storage is assigned to that direction.
     */
    public @Nullable SingleSlotStorage<ItemVariant> getSlot(int slot, Direction direction)
    {
        InventoryStorage storage = this.getStorage(direction);
        if (storage == null)
            return null;
        return storage.getSlot(slot);
    }

    /**
     * Retrieves all storage slots for the given direction.
     *
     * @param direction the direction from which to access the storage.
     * @return a list of {@link SingleSlotStorage} instances for the specified direction, or null if no storage is assigned to that direction.
     */
    public @Nullable List<SingleSlotStorage<ItemVariant>> getSlots(Direction direction)
    {
        InventoryStorage storage = this.getStorage(direction);
        if (storage == null)
            return null;
        return storage.getSlots();
    }

    /**
     * @param direction the direction to get the storage for.
     *
     * @return the storage for the given direction, or null if no storage is assigned to that direction.
     */
    public @Nullable InventoryStorage getStorage(Direction direction)
    {
        return this.sidedStorageMap.get(direction);
    }

    /**
     * @param index the index of the inventory to get.
     *
     * @return the inventory at the given index, or null if the index is out of bounds.
     */
    public @Nullable SimpleInventory getInventory(int index)
    {
        return this.inventories.get(index);
    }

    /**
     * Retrieves a list of {@link ItemStack} instances representing the contents of all inventories wrapped by this storage.
     * This method provides a unified view of all item stacks across the multiple inventories, allowing for easy access
     * and manipulation of the items stored within.
     *
     * @return a list of {@link ItemStack} instances, each representing an item stack from the wrapped inventories.
     */
    public @NotNull List<ItemStack> getStacks()
    {
        List<ItemStack> stacks = new ArrayList<>();
        for (SimpleInventory inventory : this.inventories)
            for (int i = 0; i < inventory.size(); i++)
                stacks.add(inventory.getStack(i));
        return stacks;
    }

    /**
     * Retrieves a list of {@link ItemStack} instances representing the contents of the inventory at the specified index.
     * This method provides access to the item stacks within a specific inventory, allowing for easy manipulation
     * and retrieval of items stored in that particular inventory.
     *
     * @param index the index of the inventory to retrieve the item stacks from.
     * @return a list of {@link ItemStack} instances from the inventory at the specified index, or an empty list if the index is out of bounds.
     *
     * @author jiraiyah
     */
    public @NotNull List<ItemStack> getStacks(int index)
    {
        List<ItemStack> stacks = new ArrayList<>();
        SimpleInventory inventory = getInventory(index);
        if (inventory == null)
            return stacks;
        for (int i = 0; i < inventory.size(); i++)
            stacks.add(inventory.getStack(i));
        return stacks;
    }

    /**
     * Retrieves a {@link RecipeSimpleInventory} instance that represents the inventory used for crafting recipes.
     * This method provides access to a specialized inventory designed to interact with crafting mechanics, allowing
     * for the retrieval and manipulation of items specifically for recipe processing.
     *
     * @return a {@link RecipeSimpleInventory} instance representing the inventory used for crafting recipes.
     */
    public RecipeSimpleInventory getRecipeInventory()
    {
        return new RecipeSimpleInventory(getStacks().toArray(new ItemStack[0]));
    }

    /**
     * Validates that the specified size is appropriate for the inventory. This method ensures that the inventory
     * can accommodate the given number of slots, throwing an exception if the size is invalid. This is typically
     * used to enforce constraints on inventory size during initialization or resizing operations.
     *
     * @param size the number of slots to validate for the inventory.
     * @throws IllegalArgumentException if the specified size is not valid for the inventory.
     */
    public void checkSize(int size)
    {
        if (this.inventories.stream().map(Inventory::size).reduce(9, Integer::sum) != size)
            throw new IllegalArgumentException("Size of inventories does not match the provided size: " + size);
    }

    /**
     * Handles the event when a player opens the inventory. This method is typically used to perform actions
     * such as updating the inventory state, notifying listeners, or triggering specific behaviors when a player
     * interacts with the inventory. It ensures that any necessary setup or state changes occur when the inventory
     * is accessed by a player.
     *
     * @param player the {@link PlayerEntity} who is opening the inventory. This parameter is annotated with {@link NotNull}
     *               to indicate that it must not be null when the method is called.
     */
    public void onOpen(@NotNull PlayerEntity player)
    {
        for (SimpleInventory inventory : this.inventories)
            inventory.onOpen(player);
    }

    /**
     * Handles the event when a player closes the inventory. This method is typically used to perform actions
     * such as saving the inventory state, notifying listeners, or triggering specific behaviors when a player
     * stops interacting with the inventory. It ensures that any necessary cleanup or state updates occur when
     * the inventory is closed by a player.
     *
     * @param player the {@link PlayerEntity} who is closing the inventory. This parameter is annotated with {@link NotNull}
     *               to indicate that it must not be null when the method is called.
     */
    public void onClose(@NotNull PlayerEntity player)
    {
        for (SimpleInventory inventory : this.inventories)
            inventory.onClose(player);
    }

    /**
     * Drops the contents of the inventory into the world at the specified position. This method is typically used
     * when an inventory block is broken or needs to release its items into the world. It ensures that all items
     * contained within the inventory are scattered around the given position in the world.
     *
     * @param world the {@link World} where the inventory contents should be dropped. This parameter is annotated with {@link NotNull}
     *              to indicate that it must not be null when the method is called.
     * @param pos   the {@link BlockPos} representing the position in the world where the inventory contents should be dropped.
     *              This parameter is also annotated with {@link NotNull} to indicate that it must not be null.
     */
    public void dropContents(@NotNull World world, @NotNull BlockPos pos)
    {
        for (SimpleInventory inventory : this.inventories)
            ItemScatterer.spawn(world, pos, inventory);
    }

    /**
     * Adds a default output inventory to the specified {@link NoScreenUpdatableBE} block entity. This method initializes
     * an inventory with the given size and associates it with a specific direction. It is typically used to set up
     * the output storage for a block entity, allowing items to be output in a specified direction.
     *
     * @param blockEntity the {@link NoScreenUpdatableBE} block entity to which the output inventory is added. This parameter
     *                    must not be null and represents the block entity that will contain the output inventory.
     * @param size        the size of the inventory to be created. This parameter specifies the number of slots in the
     *                    output inventory.
     * @param direction   the {@link Direction} in which the output inventory is accessible. This parameter determines
     *                    the direction from which the inventory can be interacted with.
     */
    public void addDefaultOutputInventory(NoScreenUpdatableBE blockEntity, int size, Direction direction)
    {
        addInventory(new OutputSimpleInventory(blockEntity, size));
    }

    /**
     * Serializes the inventory data into an {@link NbtList} using the provided {@link RegistryWrapper.WrapperLookup}.
     * This method is used to convert the current state of the inventory into NBT format, which can be stored or
     * transferred as needed. The serialization process involves writing each inventory's contents into the NBT structure.
     *
     * @param registryLookup the {@link RegistryWrapper.WrapperLookup} used to resolve registry entries during serialization.
     *                       This parameter provides the necessary context for handling registry-based data within the NBT.
     * @return an {@link NbtList} containing the serialized inventory data, representing the current state of the inventory.
     */
    @Override
    public NbtList writeNbt(RegistryWrapper.WrapperLookup registryLookup)
    {
        NbtList nbt = new NbtList();
        for (SimpleInventory inventory : this.inventories)
        {
            NbtCompound inventoryNbt = new NbtCompound();
            nbt.add(Inventories.writeNbt(inventoryNbt, inventory.getHeldStacks(), registryLookup));
        }
        return nbt;
    }

    /**
     * Deserializes the inventory data from the provided {@link NbtList} using the given {@link RegistryWrapper.WrapperLookup}.
     * This method is used to restore the state of the inventory from NBT format, allowing the inventory to be reconstructed
     * with its previous contents. The deserialization process involves reading each inventory's contents from the NBT structure.
     *
     * @param nbt            the {@link NbtList} containing the serialized inventory data. This parameter represents the NBT
     *                       data structure from which the inventory state will be restored.
     * @param registryLookup the {@link RegistryWrapper.WrapperLookup} used to resolve registry entries during deserialization.
     *                       This parameter provides the necessary context for handling registry-based data within the NBT.
     */
    @Override
    public void readNbt(NbtList nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        for (int index = 0; index < nbt.size(); index++)
        {
            NbtCompound inventoryNbt = nbt.getCompound(index);
            SimpleInventory inventory = this.inventories.get(index);
            Inventories.readNbt(inventoryNbt, inventory.getHeldStacks(), registryLookup);
        }
    }

    /**
     * Retrieves a `SimpleInventory` related to a direction using the facing of the block.
     *
     * @return A {@link SimpleInventory} related to the direction with the given facing.
     */
    public InventoryStorage getProvider(Direction direction, Direction facing)
    {
        return getStorage(PosHelper.getRelativeDirection(direction, facing));
    }
}