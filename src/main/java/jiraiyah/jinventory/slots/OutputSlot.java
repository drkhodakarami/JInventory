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

package jiraiyah.jinventory.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

/**
 * A specialized slot implementation that extends {@link PredicateSlot} to create an output-only slot.
 *
 * <p>This class is designed to prevent any item from being inserted into the slot by using a predicate
 * that always returns {@code false}. This makes it suitable for use in scenarios where items should
 * only be extracted from the slot, such as crafting or processing outputs.
 *
 * <p>Example usage:
 * <pre>{@code
 *     Inventory inventory = new SimpleInventory(9);
 *     OutputSlot outputSlot = new OutputSlot(inventory, 0, 10, 10);
 * }</pre>
 * This example creates an output slot at index 0 that does not allow any item insertion.
 *
 * @see PredicateSlot
 * @see Inventory
 * @see Slot
 *
 * @version 1.0
 * @since 2023
 *
 * @author TurtyWurty
 */
@SuppressWarnings("unused")
public class OutputSlot extends PredicateSlot
{
    /**
     * Constructs an {@code OutputSlot} with the specified inventory, index, and position.
     *
     * <p>This constructor initializes an output-only slot that does not allow any item insertion.
     * The slot is associated with the given inventory and positioned at the specified coordinates.
     *
     * @param inventory the inventory to which this slot belongs
     * @param index the index of the slot within the inventory
     * @param x the x-coordinate of the slot's position on the screen
     * @param y the y-coordinate of the slot's position on the screen
     */
    public OutputSlot(Inventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y, itemStack -> false);
    }
}