DELETE
FROM restaurant_menu;
DELETE
FROM order_items;
DELETE
FROM menus;
DELETE
FROM orders;
DELETE
FROM restaurants;
DELETE
FROM users;

INSERT INTO users (id, first_name, last_name, username, email, password, phone_number, address)
VALUES (2, 'Szabo-Temple', 'Oliver', 'Oli2', 'Oli2@gmail.com',
        '$2a$12$dGegYUGfeh8gXQW4cJNDeu.f6a6a64.uLvJalgMwriblz7pzR9Nt6', '+3630123456', 'budakeszi'),
       (3, 'Szabo-Temple', 'Oliver', 'Oli3', 'Oli3@gmail.com',
        '$2a$12$dGegYUGfeh8gXQW4cJNDeu.f6a6a64.uLvJalgMwriblz7pzR9Nt6', '+3630123456', 'budakeszi'),
       (4, 'emptyOrder', 'emptyOrder', 'emptyOrder', 'emptyOrder@gmail.com',
        '$2a$12$dGegYUGfeh8gXQW4cJNDeu.f6a6a64.uLvJalgMwriblz7pzR9Nt6', '+3630123456', 'budakeszi'),
       (5, 'createNewOrderUser', 'createNewOrderUser', 'createNewOrderUser', 'createNewOrderUser@gmail.com',
        '$2a$12$dGegYUGfeh8gXQW4cJNDeu.f6a6a64.uLvJalgMwriblz7pzR9Nt6', '+3630123456', 'budakeszi'),
       (6, 'setNewStatusUser', 'setNewStatusUser', 'setNewStatusUser', 'setNewStatusUser@gmail.com',
        '$2a$12$dGegYUGfeh8gXQW4cJNDeu.f6a6a64.uLvJalgMwriblz7pzR9Nt6', '+3630123456', 'budakeszi'),
       (7, 'deleteOrderUser', 'deleteOrderUser', 'deleteOrderUser', 'deleteOrderUser@gmail.com',
        '$2a$12$dGegYUGfeh8gXQW4cJNDeu.f6a6a64.uLvJalgMwriblz7pzR9Nt6', '+3630123456', 'budakeszi');



INSERT INTO restaurants (id, name, phone_number, address)
VALUES (1, 'McDonalds', '+3612345', 'McDonalds utca'),
       (2, 'Burger King', '+3654321', 'Burger King utca'),
       (3, 'Belozzo', '+362468', 'Belozzo utca'),
       (4, 'Kfc', '+368642', 'Kfc utca'),
       (5, 'Wasabi', '+3613579', 'Wasabi utca'),
       (6, 'Vapiano', '+3697531', 'Vapiano utca'),
       (7, 'Pizza Hut', '+362222', 'Pizza Hut utca');


INSERT INTO menus (id, name, description, price)
VALUES (1, 'Sajtburger menü', 'A sajtburger menü tartalmaz egy húspogácsát, sajtot, salátát, paradicsomot, hagymát, savanyú uborkát és egy zsemlét. Illetve jár hozzá egy adag sült krumpli és egy ital.', 800),
       (2, 'Big mac menü', 'Ez egy népszerű gyorsétel-étel, amely két marhahúspogácsát, salátát, sajtot, savanyú uborkát, hagymát és különleges szószt tartalmaz szezámmagos zsemlén. Az ételhez jár sült krumpli és ital is.', 1300),
       (3, 'McDonalds special menü', 'Ez egy különleges menü a McDonald''s-ból, amely többféle ételt tartalmazhat, például hamburgert, csirkés szendvicset, nuggetset, sültkrumplit és italt.', 2100),
       (4, 'Bolognai Spagetti', 'Ez egy klasszikus olasz étel spagetti tésztából és egy húsos paradicsomszószból, amely marha- vagy sertéshúst tartalmaz. A szószba hagyma, fokhagyma, fűszerek és ízesítők is kerülhetnek.', 1700),
       (5, 'Margharita pizza', 'Ez egy klasszikus olasz pizza, amely általában paradicsomszószból, friss mozzarella sajtból és friss bazsalikomlevelekből áll. Ez egy egyszerű, de ízletes pizza, amelyet világszerte élveznek.', 2500),
       (6, 'Carbonara spagetti', 'Ez egy klasszikus olasz tésztaétel, amely spagetti tésztából és egy krémes szószból készül, amely tojásból, parmezán sajtból és baconből vagy pancettából készül.', 1800),
       (7, 'Keddi kosár', '2 darab Kentucky csirkerész, 4 darab csípős Hot Wings csirkeszárny, 4 darab csípős Strips csirkemell csík, 2 kis adag sültburgonya, 1 darab választható szósz.', 1990),
       (8, 'Sushi', 'Ez egy japán étel, amely nyers hal- vagy tenger gyümölcsei darabokból áll, amelyeket fűszeres rizzsel tálalnak. Tartalmazhat zöldségeket', 4980),
       (9, 'Zölséges pirított csirke', 'Rántott csirkével és zöldségekkel készült kínai étel tésztával tálalva.', 3580),
       (10, 'Négy sajtos pizza', 'Négyféle sajtból készült pizza, jellemzően mozzarella, parmezán, gorgonzola és provolone.', 2760),
       (11, 'BBQ Pizza', 'Egy pizza barbecue szósszal, sajttal és különféle választható feltétekkel, például csirkével, hagymával és paprikával.', 3500);

INSERT INTO restaurant_menu (restaurant_id, menu_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 4),
       (3, 6),
       (4, 7),
       (4, 1),
       (5, 8),
       (5, 9),
       (6, 4),
       (6, 5),
       (6, 10),
       (7, 5),
       (7, 10),
       (7, 11);

INSERT INTO orders (id, total_amount, delivery_address, status, created, user_id, restaurant_id)
VALUES (1001, 3400, 'budakeszi', 'ORDER_CONFIRMED', 1681065838, 2, 1),
       (1002, 3400, 'budakeszi', 'READY_FOR_DELIVERY', 1681118702, 3, 1),
       (1003, 3400, 'budakeszi', 'DELIVERED', 1681065838, 2, 1),
       (1004, 3400, 'budakeszi', 'ORDER_CONFIRMED', 1681065838, 6, 1),
       (1005, 3400, 'budakeszi', 'ORDER_CONFIRMED', 1681065838, 6, 1),
       (1006, 3400, 'budakeszi', 'ORDER_CONFIRMED', 1681065838, 7, 1),
       (1007, 2100, 'budakeszi', 'ORDER_CONFIRMED', 1681065838, 7, 1);

INSERT INTO order_items (id, quantity, price, order_id, menu_id)
VALUES (1001, 1, 800, 1001, 1),
       (1002, 2, 2600, 1001, 2),
       (1003, 1, 800, 1002, 1),
       (1004, 2, 2600, 1002, 2),
       (1005, 1, 800, 1003, 1),
       (1006, 2, 2600, 1003, 2),
       (1007, 1, 800, 1004, 1),
       (1008, 2, 2600, 1004, 2),
       (1009, 1, 800, 1005, 1),
       (1010, 2, 2600, 1005, 2),
       (1011, 1, 800, 1006, 1),
       (1012, 2, 2600, 1006, 2),
       (1013, 1, 800, 1007, 1),
       (1014, 1, 1300, 1007, 2);
