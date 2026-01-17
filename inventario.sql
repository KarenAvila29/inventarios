drop table if exists categorias;
drop table if exists categoria_unidad_medida;
drop table if exists unidad_medida;
drop table if exists producto;
drop table if exists historial_stock;
drop table if exists estado_pedido;
drop table if exists tipo_documento;
drop table if exists proveedor;
drop table if existscabecera_venta;

create table categorias(
	codigo_cat serial not null,
	nombre varchar(100) not null,
	categoria_padre int ,
	constraint categorias_pk primary key(codigo_cat),
	constraint categorias_fk foreign key(categoria_padre)
	references categorias(codigo_cat)
);

create table categoria_unidad_medida(
	codigo char(1) not null,
	nombre varchar(100) not null,
	constraint categoria_unidad_medida_pk primary key(codigo)
);

create table unidad_medida(
	codigo char(5) not null,
	descripcion varchar(100) not null,
	categoria_udm char(1) not null,
 	constraint unidad_medida_pk primary key(codigo),
	constraint categoria_fk foreign key(categoria_udm)
	references categoria_unidad_medida(codigo)
);
create table producto(
	codigo_pro char(5) not null,
	nombre varchar(100),
	umd char(5),
	precio_venta money,
	tiene_iva boolean,
	coste money,
	categoria int,
	stock int,
 	constraint producto_pk primary key(codigo_pro),
	constraint producto_medida_fk foreign key(umd)
	references unidad_medida(codigo),
	constraint producto_categoria_fk foreign key(categoria)
	references categorias(codigo_cat)
);

create table historial_stock(
	codigo serial not null,
	fecha TIMESTAMP,
	referencia varchar(100),
	producto_id char(5),
	cantidad int,	
 	constraint historial_stock_pk primary key(codigo),
	constraint historial_pro_stock_fk foreign key(producto_id)
	references producto(codigo_pro)
);
create table estado_pedido(
	codigo char(1) not null,
	descripcion varchar(100),
	constraint estado_pedido_pk primary key(codigo)	
);

create table tipo_documento(
	codigo char(1) not null,
	descripcion varchar(50),
	constraint tipo_documento_pk primary key(codigo)
);

create table proveedor(
	identificacion varchar(15) not null,
	tipo_docum char(1) not null,
	nombre varchar(100),
	telefono varchar(10), 
	correo varchar(50),
	direccion varchar(100),
	constraint proveedor_pk primary key(identificacion),
	constraint proveedor_documento_fk foreign key(tipo_docum)
	references tipo_documento(codigo)
);

create table cabecera_venta(
	codigo int not null,
	fecha timestamp,
	subtotal money,
	iva money,
	total money,
	constraint cabecera_venta_pk primary key(codigo)
);

create table detalle_venta(
	codigo int not null,
	cabecera int not null,
	producto char(5) not null,
	cantidad int not null,
	precio_venta money,
	subtotal money,
	total_iva money,
);


--//INGRESO DE DATOS TABLA CATEGORIAS//--
insert into categorias(nombre,categoria_padre)
values ('Materia prima',null);
insert into categorias(nombre,categoria_padre)
values ('Proteina',1);
insert into categorias(nombre,categoria_padre)
values ('Salsas',1);
insert into categorias(nombre,categoria_padre)
values ('Punto de venta',null);
insert into categorias(nombre,categoria_padre)
values ('Bebidas',4);
insert into categorias(nombre,categoria_padre)
values ('Con alcohol',5);
insert into categorias(nombre,categoria_padre)
values ('Sin alcohol',5);

--INGRESO DE DATOS TABLA CATEGORIA UNIDAD MEDIDA--
insert into categoria_unidad_medida(codigo,nombre)
values ('U','Unidad');
insert into categoria_unidad_medida(codigo,nombre)
values ('V','Volumen');
insert into categoria_unidad_medida(codigo,nombre)
values ('P','Peso');
insert into categoria_unidad_medida(codigo,nombre)
values ('L','Longitud');

--INGRESO DE DATOS TABLA UNIDADES DE MEDIDAS--
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('ml','Mililitros','V');
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('l','Litros','V');
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('u','Unidad','U');
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('d','Docena','U');
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('g','Gramos','P');
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('kg','Kilogramos','P');
insert into unidad_medida(codigo,descripcion,categoria_udm)
values ('lb','Libra','P');

--INGRESO DE DATOS TABLA PRODUCTO--
insert into producto(codigo_pro,nombre,umd,precio_venta,tiene_iva,coste,categoria,stock)
values ('P0001','Coca Cola','u',0.5804,true,0.3729,7,100);
insert into producto(codigo_pro,nombre,umd,precio_venta,tiene_iva,coste,categoria,stock)
values ('P0002','Salsa de Tomate','kg',0.95,true,0.8736,3,10);
insert into producto(codigo_pro,nombre,umd,precio_venta,tiene_iva,coste,categoria,stock)
values ('P0003','Mostaza','kg',0.95,true,0.89,3,50);


--//INGRESO DE DATOS TABLA HISTORIAL_STOCK//--
insert into historial_stock(fecha,referencia,producto_id,cantidad)
values ('2026-01-14 12:48:00','Pedido 1','P0001',100);
insert into historial_stock(fecha,referencia,producto_id,cantidad)
values ('2026-01-14 12:48:00','Pedido 1','P0002',50);
insert into historial_stock(fecha,referencia,producto_id,cantidad)
values ('2026-01-14 13:10:00','Pedido 2','P0001',10);
insert into historial_stock(fecha,referencia,producto_id,cantidad)
values ('2026-01-14 13:10:00','Venta 1','P0001',-5);
insert into historial_stock(fecha,referencia,producto_id,cantidad)
values ('2026-01-14 12:10:00','Venta1','P0002',-1);

--//INGRESO DE DATOS TABLA ESTADO_PEDIDOS//--
insert into estado_pedido(codigo,descripcion)
values ('S','Solicitado');
insert into estado_pedido(codigo,descripcion)
values ('R','Recibido');

--//INGRESO DE DATOS TABLA TIPO DOCUMENTO//--
insert into tipo_documento(codigo,descripcion)
values('C','Cedula');
insert into tipo_documento(codigo,descripcion)
values('R','Ruc');

--//INGRESO DE DATOS TABLA PROVEEDOR//--
insert into proveedor(identificacion,tipo_docum,nombre,telefono,correo,direccion)
values ('1725168130001','R','Karen Avila', '0995825180','avilamore20@hotmail.com','Quito');
insert into proveedor(identificacion,tipo_docum,nombre,telefono,correo,direccion)
values ('1717270472','C','Maritza Aguilar', '0990514236','aguilar@hotmail.com','Guayaquil');

--//INGRESO DE DATOS TABLA CABECERA_VENTAS//--
insert into cabecera_venta(codigo,fecha,subtotal,iva,total)
values (1,'2026-01-14 13:10:00',2.826086,0.423914,3.25);

select *from categorias
select *from categoria_unidad_medida
select *from unidad_medida
select *from producto
select *from historial_stock
select *from estado_pedido
select *from tipo_documento
select *from proveedor
select *from existscabecera_venta