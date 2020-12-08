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
