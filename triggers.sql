CREATE TRIGGER AfterInsertCartProduct
    AFTER INSERT
    ON CartProduct
    FOR EACH ROW
BEGIN
    UPDATE Cart
    SET totalPrice = totalPrice + NEW.count * (
        SELECT price
        FROM Product
        WHERE Product.id = NEW.productId
    )
    WHERE Cart.id = NEW.cartId;
END;

CREATE TRIGGER AfterDeleteCartProduct
    AFTER DELETE
    ON CartProduct
    FOR EACH ROW
BEGIN
    UPDATE Cart
    SET totalPrice = totalPrice - OLD.count * (
        SELECT price
        FROM Product
        WHERE Product.id = OLD.productId
    )
    WHERE Cart.id = OLD.cartId;
END;

CREATE TRIGGER AfterCreateOrder
    AFTER INSERT
    ON OrderTable
    FOR EACH ROW
BEGIN
    UPDATE Cart
    SET isOrdered = true
    WHERE Cart.id = NEW.cartId;
END;

CREATE TRIGGER AfterCreateOrderProductStore
    AFTER INSERT
    ON OrderProductStore
    FOR EACH ROW
BEGIN
    UPDATE ProductStore
    SET ProductStore.count = ProductStore.count - NEW.count
    WHERE ProductStore.storeId = NEW.storeId
      AND ProductStore.productId = NEW.productId;
END;

CREATE TRIGGER AfterDeleteOrderProductStore
    AFTER DELETE
    ON OrderProductStore
    FOR EACH ROW
BEGIN
    UPDATE ProductStore
    SET ProductStore.count = ProductStore.count + OLD.count
    WHERE ProductStore.storeId = OLD.storeId
      AND ProductStore.productId = OLD.productId;
END;
