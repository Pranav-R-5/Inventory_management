create database inventory_management
create table products(
	p_id int primary key,
	p_name varchar(20),
	price int,
	quantity int,
	created_date date,
	updated_date date,
	description varchar(50)
)
INSERT INTO public.products
(p_id, p_name, price, quantity, created_date, updated_date, description)
VALUES(1, 'iphone', 80000, 10, '2020-10-10', '2020-10-10', 'iphone 15 with a15 bionic chip');
INSERT INTO public.products
(p_id, p_name, price, quantity, created_date, updated_date, description)
VALUES(2, 'Macbook', 120000, 30, '2021-11-11', '2021-11-11', 'macbook air with 12 gb ram');
INSERT INTO public.products
(p_id, p_name, price, quantity, created_date, updated_date, description)
VALUES(3, 'iwatch',30000,20,'2022-12-12', '2022-12-12', 'iwatch with calling feature and a10 chip');
INSERT INTO public.products
(p_id, p_name, price, quantity, created_date, updated_date, description)
VALUES(4, 'Airtag', 20000, 50, '2023-01-13', '2023-01-13', 'used to find locations');
INSERT INTO public.products
(p_id, p_name, price, quantity, created_date, updated_date, description)
VALUES(5, 'Airtag', 20000, 50, '2023-01-13', '2023-01-13', 'used to find locations');

create table purchases(
	p_id int,
	purchase_id int primary key,
	purchase_quantity int,
	purchase_date date,
	foreign key (p_id) references products(p_id)
)

INSERT INTO public.purchases
(p_id, purchase_id, purchase_quantity, purchase_date)
VALUES(1,1, 5, '2021-11-11');

INSERT INTO public.purchases
(p_id, purchase_id, purchase_quantity, purchase_date)
VALUES(2,2, 4, '2021-12-12');

INSERT INTO public.purchases
(p_id, purchase_id, purchase_quantity, purchase_date)
VALUES(5, 3, 20, '2023-3-14');

select * from products
select * from purchases 

select * from products 
where price>20000

update products 
set p_name='ipad'
where p_id=5;

alter table purchases 
rename column purchase_date to order_date; 

//indexing
create index idx_p_name on products(p_name)

select * from purchases pu 
left join products pr 
on pu.p_id=pr.p_id


select * from purchases pu 
right join products pr 
on pu.p_id=pr.p_id

select purchase_id,p_name,quantity,purchase_quantity
from purchases pu left join products pr
on pu.p_id=pr.p_id 
order by purchase_quantity



select purchase_id ,max(price) 
from purchases pu 
right join products pr on pu.p_id=pr.p_id 
group by purchase_id
order by max 
