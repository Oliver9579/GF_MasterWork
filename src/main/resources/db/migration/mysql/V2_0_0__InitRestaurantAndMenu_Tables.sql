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