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

package jiraiyah.jinventory.interfaces;

import jiraiyah.jinventory.WrappedInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * Interface representing a wrapped inventory that provides access to its underlying storage mechanisms.
 * This interface defines methods to retrieve the wrapped inventory, its storage, and the output inventory.
 *
 * <p>The wrapped inventory is a collection of multiple inventories that can be accessed and managed
 * collectively. It supports operations such as retrieving the inventory storage for specific directions
 * and accessing the output inventory.</p>
 *
 * Wrapped inventory provides the needed flexibility in the types of inventories that can be
 * wrapped and managed.
 */
@SuppressWarnings("unused")
public interface IWrappedInventory
{
    /**
     * Retrieves the wrapped inventory storage.
     *
     * <p>This method returns an instance of {@link WrappedInventoryStorage} that contains the wrapped
     * inventories. The wrapped inventory storage provides access to the underlying inventories and
     * their storage operations.</p>
     *
     * @return an instance of {@link WrappedInventoryStorage} containing the wrapped inventories.
     */
    WrappedInventoryStorage getInventory();

    /**
     * Retrieves the inventory storage for a specific direction.
     *
     * <p>This method allows access to the inventory storage from a specified direction. If the direction
     * is null, the default storage is returned. The inventory storage provides methods to interact with
     * the inventory's contents and manage its storage capabilities.</p>
     *
     * @param direction the {@link Direction} from which to access the inventory storage. If null, the default storage is returned.
     * @return an instance of {@link InventoryStorage} for the specified direction, or the default storage if direction is null.
     */
    InventoryStorage getInventoryStorage(@Nullable Direction direction);

    /**
     * Retrieves the output inventory associated with this wrapped inventory.
     *
     * <p>The output inventory is a specialized inventory used for output operations. This method provides
     * access to the output inventory, allowing for interaction with its contents and management of its
     * storage capabilities.</p>
     *
     * @return an instance of {@link SimpleInventory} representing the output inventory.
     */
    SimpleInventory getOutputInventory();
}