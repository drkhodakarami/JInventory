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
 * A specialized inventory class that extends {@link SyncingSimpleInventory} and
 * incorporates a {@link BiPredicate} to validate items in specific slots.
 * This class allows for custom validation logic to be applied to inventory slots,
 * ensuring that only items meeting certain criteria can be placed in specific slots.
 *
 * <p>This class provides constructors to initialize the inventory with a specified
 * size or with a predefined set of {@link ItemStack} items, along with a predicate
 * for validation. It overrides the {@code isValid} method to utilize the predicate
 * for determining the validity of items in slots.</p>
 *
 * <p>Usage of this class is ideal in scenarios where inventory slots require
 * specific validation logic, such as restricting certain items to specific slots
 * based on custom rules.</p>
 *
 * @see SyncingSimpleInventory
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
public class PredicateSimpleInventory extends SyncingSimpleInventory
{
    /**
     * A predicate used to validate items in specific slots of the inventory.
     * This {@link BiPredicate} takes an {@link ItemStack} and an {@link Integer}
     * representing the slot index as parameters and returns a boolean indicating
     * whether the item is valid for the given slot.
     *
     * <p>This predicate allows for custom validation logic to be applied to
     * inventory slots, ensuring that only items meeting certain criteria can
     * be placed in specific slots. It is particularly useful for implementing
     * rules such as restricting certain items to specific slots based on custom
     * conditions.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * BiPredicate<ItemStack, Integer> customPredicate = (itemStack, slotIndex) -> {
     *     // Custom validation logic
     *     return itemStack.getItem() instanceof SpecificItem && slotIndex == 0;
     * };
     * }</pre>
     *
     * @see ItemStack
     * @see BiPredicate
     */
    private final BiPredicate<ItemStack, Integer> predicate;

    /**
     * Constructs a new instance of {@code PredicateSimpleInventory} with the specified
     * block entity, inventory size, and item validation predicate.
     *
     * <p>This constructor initializes the inventory with a given size and associates
     * it with an {@link UpdatableBE} block entity. It also sets a {@link BiPredicate}
     * to validate items for specific slots, allowing for custom rules to be applied
     * to the inventory.</p>
     *
     * @param blockEntity The {@link UpdatableBE} block entity associated with this inventory.
     *                    This entity is responsible for handling updates between the server
     *                    and client.
     * @param size        The size of the inventory, representing the number of slots available.
     * @param predicate   A {@link BiPredicate} that takes an {@link ItemStack} and an {@link Integer}
     *                    representing the slot index, and returns a boolean indicating whether
     *                    the item is valid for the specified slot. This allows for custom validation
     *                    logic to be applied to the inventory slots.
     *
     * @see UpdatableBE
     * @see BiPredicate
     * @see ItemStack
     */
    public PredicateSimpleInventory(UpdatableBE blockEntity, int size, BiPredicate<ItemStack, Integer> predicate)
    {
        super(blockEntity, size);
        this.predicate = predicate;
    }

    /**
     * Constructs a new instance of {@code PredicateSimpleInventory} with the specified
     * block entity, item validation predicate, and initial set of items.
     *
     * <p>This constructor initializes the inventory with a predefined set of {@link ItemStack}
     * items and associates it with an {@link UpdatableBE} block entity. It also sets a
     * {@link BiPredicate} to validate items for specific slots, allowing for custom rules
     * to be applied to the inventory.</p>
     *
     * @param blockEntity The {@link UpdatableBE} block entity associated with this inventory.
     *                    This entity is responsible for handling updates between the server
     *                    and client.
     * @param predicate   A {@link BiPredicate} that takes an {@link ItemStack} and an {@link Integer}
     *                    representing the slot index, and returns a boolean indicating whether
     *                    the item is valid for the specified slot. This allows for custom validation
     *                    logic to be applied to the inventory slots.
     * @param items       An array of {@link ItemStack} representing the initial items to populate
     *                    the inventory. These items are placed into the inventory slots upon
     *                    initialization.
     *
     * @see UpdatableBE
     * @see BiPredicate
     * @see ItemStack
     */
    public PredicateSimpleInventory(UpdatableBE blockEntity, BiPredicate<ItemStack, Integer> predicate, ItemStack... items)
    {
        super(blockEntity, items);
        this.predicate = predicate;
    }

    /**
     * Determines whether the specified {@link ItemStack} is valid for the given slot index
     * in the inventory. This method utilizes the predefined {@link BiPredicate} to apply
     * custom validation logic, ensuring that only items meeting certain criteria can be
     * placed in specific slots.
     *
     * @param slot  The index of the slot in the inventory to validate the item against.
     * @param stack The {@link ItemStack} to be validated for the specified slot.
     * @return {@code true} if the item is valid for the specified slot according to the
     *         custom validation logic; {@code false} otherwise.
     *
     * @see ItemStack
     * @see BiPredicate
     */
    @Override
    public boolean isValid(int slot, ItemStack stack)
    {
        return this.predicate.test(stack, slot);
    }

    /**
     * Retrieves the {@link BiPredicate} used for validating items in specific slots
     * of the inventory. This predicate allows for custom validation logic to be
     * applied, ensuring that only items meeting certain criteria can be placed
     * in specific slots.
     *
     * @return The {@link BiPredicate} that takes an {@link ItemStack} and an {@link Integer}
     *         representing the slot index, and returns a boolean indicating whether
     *         the item is valid for the specified slot.
     *
     * @see ItemStack
     * @see BiPredicate
     */
    public BiPredicate<ItemStack, Integer> getPredicate()
    {
        return this.predicate;
    }
}