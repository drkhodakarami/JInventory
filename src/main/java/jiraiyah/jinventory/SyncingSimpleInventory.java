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

import jiraiyah.jiralib.blockentity.UpdatableBE;
import net.minecraft.item.ItemStack;

/**
 * A specialized inventory class that extends {@link RecipeSimpleInventory} and
 * synchronizes changes with an associated {@link UpdatableBE} block entity.
 * This class ensures that any modifications to the inventory are reflected
 * in the block entity, allowing for seamless updates between the server and client.
 *
 * <p>This class provides constructors to initialize the inventory with a specified
 * size or with a predefined set of {@link ItemStack} items. It overrides the
 * {@code markDirty} method to trigger an update on the associated block entity
 * whenever the inventory is modified.</p>
 *
 * <p>Usage of this class is ideal in scenarios where inventory changes need to
 * be synchronized with a block entity, ensuring that the client and server states
 * remain consistent.</p>
 *
 * @see RecipeSimpleInventory
 * @see UpdatableBE
 * @see ItemStack
 *
 * @author TurtyWurty
 */
@SuppressWarnings("unused")
public class SyncingSimpleInventory extends RecipeSimpleInventory
{
    /**
     * The block entity associated with this inventory. This block entity is
     * responsible for handling updates between the server and client whenever
     * the inventory changes. It ensures that any modifications to the inventory
     * are synchronized with the block entity, maintaining consistency across
     * different game states.
     */
    private final UpdatableBE blockEntity;

    /**
     * Constructs a new `SyncingSimpleInventory` with a specified size and associates it
     * with a given `UpdatableBE` block entity. This constructor initializes the inventory
     * to the specified size and ensures that any changes to the inventory will trigger
     * updates in the associated block entity.
     *
     * @param blockEntity The `UpdatableBE` block entity that will be updated whenever
     *                    the inventory changes. This ensures synchronization between
     *                    the server and client.
     * @param size        The size of the inventory to be created. This determines the
     *                    number of slots available in the inventory.
     */
    public SyncingSimpleInventory(UpdatableBE blockEntity, int size)
    {
        super(size);
        this.blockEntity = blockEntity;
    }

    /**
     * Constructs a new `SyncingSimpleInventory` with a predefined set of `ItemStack` items
     * and associates it with a given `UpdatableBE` block entity. This constructor initializes
     * the inventory with the specified items and ensures that any changes to the inventory
     * will trigger updates in the associated block entity.
     *
     * @param blockEntity The `UpdatableBE` block entity that will be updated whenever
     *                    the inventory changes. This ensures synchronization between
     *                    the server and client.
     * @param items       The initial set of `ItemStack` items to populate the inventory.
     *                    These items determine the starting state of the inventory.
     */
    public SyncingSimpleInventory(UpdatableBE blockEntity, ItemStack... items)
    {
        super(items);
        this.blockEntity = blockEntity;
    }

    /**
     * Marks the inventory as dirty, indicating that its contents have changed.
     * This method overrides the parent class's `markDirty` method to ensure
     * that any changes to the inventory are also reflected in the associated
     * `UpdatableBE` block entity. By calling this method, the block entity
     * is notified to update its state, ensuring synchronization between the
     * server and client.
     */
    @Override
    public void markDirty()
    {
        super.markDirty();
        this.blockEntity.update();
    }

    /**
     * Retrieves the associated `UpdatableBE` block entity for this inventory.
     * This method provides access to the block entity that is responsible for
     * handling updates and synchronization between the server and client whenever
     * the inventory changes.
     *
     * @return The `UpdatableBE` block entity linked to this inventory, which
     *         ensures that any modifications to the inventory are reflected
     *         in the block entity.
     */
    public UpdatableBE getBlockEntity()
    {
        return this.blockEntity;
    }
}