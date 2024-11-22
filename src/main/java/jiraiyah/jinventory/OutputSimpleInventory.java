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

import java.util.function.BiPredicate;

/**
 * A specialized inventory class that extends {@link PredicateSimpleInventory}
 * and is designed specifically for output-only slots. This class ensures that
 * no items can be placed into the inventory slots by using a {@link BiPredicate}
 * that always returns {@code false}.
 *
 * <p>The {@code OutputSimpleInventory} class is useful in scenarios where
 * inventory slots are intended solely for output purposes, preventing any
 * manual insertion of items. It provides constructors to initialize the
 * inventory with a specified size or with a predefined set of {@link ItemStack}
 * items, while enforcing the output-only restriction.</p>
 *
 * <p>Usage of this class is ideal in cases where inventory slots should
 * only be populated programmatically, such as in automated systems or
 * processing machines.</p>
 *
 * @see PredicateSimpleInventory
 * @see BiPredicate
 * @see ItemStack
 * @see UpdatableBE
 *
 * @version 1.0
 * @since 2023
 *
 * @author TurtyWurty
 */
@SuppressWarnings("unused")
public class OutputSimpleInventory extends PredicateSimpleInventory
{
    /**
     * Constructs an {@code OutputSimpleInventory} with a specified size, associated with a given
     * {@link UpdatableBE} block entity. This constructor initializes the inventory with the
     * specified number of slots, ensuring that all slots are output-only.
     *
     * <p>This constructor is useful when you need to create an inventory that is tied to a specific
     * block entity and is intended for output purposes only. The inventory will not allow any items
     * to be manually inserted, maintaining the integrity of the output-only design.</p>
     *
     * @param blockEntity The {@link UpdatableBE} block entity associated with this inventory.
     *                    This parameter links the inventory to a specific block entity, allowing
     *                    for updates and synchronization between server and client.
     * @param size        The number of slots in the inventory. This determines the capacity of
     *                    the inventory, with each slot being designated as output-only.
     */
    public OutputSimpleInventory(UpdatableBE blockEntity, int size)
    {
        super(blockEntity, size, (slot, stack) -> false);
    }

    /**
     * Constructs an {@code OutputSimpleInventory} associated with a given {@link UpdatableBE} block entity
     * and initializes it with a predefined set of {@link ItemStack} items. This constructor sets up the
     * inventory to be output-only, ensuring that no items can be manually inserted into the slots.
     *
     * <p>This constructor is particularly useful when you need to create an inventory that is pre-populated
     * with specific items and is linked to a block entity for synchronization purposes. The inventory
     * maintains its output-only nature, making it suitable for automated systems or machines where
     * manual item insertion is not desired.</p>
     *
     * @param blockEntity The {@link UpdatableBE} block entity associated with this inventory. This parameter
     *                    establishes a connection between the inventory and the block entity, allowing for
     *                    updates and synchronization between server and client.
     * @param items       A varargs parameter of {@link ItemStack} representing the initial items to populate
     *                    the inventory slots. Each slot is designated as output-only, and the items are
     *                    set programmatically.
     */
    public OutputSimpleInventory(UpdatableBE blockEntity, ItemStack... items)
    {
        super(blockEntity, (slot, stack) -> false, items);
    }
}