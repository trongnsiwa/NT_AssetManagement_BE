INSERT INTO public.asset_states (id,"name") VALUES
(1,'ASSIGNED'),
(2,'AVAILABLE'),
(3,'NOT_AVAILABLE'),
(4,'WAITING_FOR_RECYCLED'),
(5,'RECYCLED'),
(6,'WAITING_FOR_ASSIGNED');

INSERT INTO public.assignment_state (id,"name") VALUES
(1,'ACCEPT'),
(2,'WAITING_FOR_ACCEPTANCE'),
(3,'DECLINED');

INSERT INTO public.locations (id,"name") VALUES
(1,'HCM'),
(2,'HN');

INSERT INTO public.roles (id,"name") VALUES
(1,'ROLE_ADMIN'),
(2,'ROLE_STAFF');

INSERT INTO public.categories (id,"name",prefix) VALUES
(1,'Bluetooth','BL'),
(2,'Iphone','IP'),
(3,'Ipad','IA'),
(4,'Monitor','MN'),
(5,'Keyboard','KB'),
(6,'Headphone','HP'),
(7,'Laptop','LT'),
(8,'Touch Pen','TP'),
(9,'Tablet','TL'),
(10,'Smart Phone','SP');
INSERT INTO public.categories (id,"name",prefix) VALUES
(11,'Personal Computer','PC'),
(12,'Bluetooth Monitor','BM'),
(13,'Personal Monitor','PM'),
(14,'Personal Laptop','PL'),
(15,'Micro','MI'),
(16,'Micro Blue','MB'),
(17,'Micro 2','M2'),
(18,'test','KK'),
(19,'Smart Watch','SW'),
(20,'Adapter','AD');
INSERT INTO public.categories (id,"name",prefix) VALUES
(21,'Chip','CH');

INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0025','2021-08-10 14:46:04.25528','2001-02-01 00:00:00','a',false,true,true,'2021-08-11 00:00:00','a','$2a$10$AyPB5JioIneGGtJUPw.KTeaYSWpciUxLvQpVY3xXZXR4bdF5XI/0K','2021-08-10 14:46:04.25628','aa',1,2),
('SD0024','2021-08-09 22:38:14.940268','2001-01-04 00:00:00','Tin',true,false,true,'2021-08-04 00:00:00','Nguyen Huu','$2a$10$m/eZcuMSEoLKohvOALkVy.fH001USs7096Ti19mNc/ZO.8oVfwFZe','2021-08-09 22:38:14.940268','tinnh',1,1),
('SD0026','2021-08-10 15:22:16.478957','2000-01-02 00:00:00','Tin',true,true,true,'2021-08-11 00:00:00','Nguyen','$2a$10$6Y3Xq.Zf9Iun2oQHxPfmoeEccO3zaK2UAQHHPWUbr6JT63mi5lXti','2021-08-10 15:22:16.478957','tinn',1,2),
('SD0028','2021-08-10 16:39:17.502049','2001-01-05 00:00:00','Tin',true,false,true,'2021-08-11 00:00:00','Nguyen','$2a$10$zBnX6.CHyUpX2ZkaMG9ZEeOmtbpZJXFRc5qanA0RvzUo5yjWTs/3i','2021-08-11 09:13:02.761525','tinn1',1,2),
('SD0029','2021-08-11 09:13:57.553227','2000-01-02 00:00:00','Trong',true,false,false,'2021-08-12 00:00:00','Nguyen','$2a$10$g9aC91jMH0NaCIkQnfLUA.aaZtE7FldUi1DfEABwxPv34Z.aNEEM2','2021-08-11 09:16:40.528815','trongn1',1,2),
('SD0030','2021-08-11 11:07:52.983464','2000-01-03 00:00:00','Trong',true,false,true,'2021-08-12 00:00:00','Nguyen Si','$2a$10$ua7iO/rnLGqDiGTD2UoMXeZN6R3u0SYqMrdIDcZVBfdv7XLshmEby','2021-08-11 11:08:26.021646','trongns1',1,1);
INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0033','2021-08-14 21:44:42.567161','1999-03-04 00:00:00','aaaaaaaaaaaaaaa',false,false,true,'2021-07-28 00:00:00','aaaaaaaaaaaaaaaaaaaa','$2a$10$XilJEH1KGHsXETO36uGl1uD/PRXt/Ryyxp0fqpYh74aAxXUDKcF4e','2021-08-14 21:44:42.567161','aaaaaaaaaaaaaaaa',1,2),
('SD0034','2021-08-14 22:54:28.286504','2000-02-03 00:00:00','Trong',true,false,false,'2021-08-19 00:00:00','Tran','$2a$10$Ymznr7Dh3Dad6z8xmdIqYOSOH6Pr1M3DJHcd56rymQSozs/oxcdBS','2021-08-14 22:55:00.980586','trongt',1,1),
('SD0023','2021-08-09 22:37:07.294233','1999-11-02 00:00:00','Trong',true,false,false,'2021-06-23 00:00:00','Nguyen Si','$2a$10$1Fe4KqKO9CF3N4HEk/vCBedW5Ww2LoZBa76mmX.TflAV73dpD3V2e','2021-08-16 06:33:54.496074','trongns',1,1),
('SD0059','2021-08-16 04:12:28.267234','2000-01-13 00:00:00','Admin',true,false,false,'2021-08-18 00:00:00','Test HN','$2a$10$TuH.wlt1/S7amGFo6eFH/Oxni/UCKmo.90OB0gVkXZb5ZkjDKD6YG','2021-08-16 04:15:40.274368','adminth1',2,1),
('SD0060','2021-08-16 04:12:51.134083','2001-01-18 00:00:00','Staff',true,false,false,'2021-08-17 00:00:00','Test','$2a$10$r1ec168lvG.T0Jcpe5pn8ewyJhyjjdl7o0n2GoysFJIoLavV6erOK','2021-08-16 04:16:04.816212','stafft',1,2),
('SD0032','2021-08-14 17:11:37.865529','1999-12-31 00:00:00','a',true,false,true,'2021-06-22 00:00:00','b','$2a$10$cXV62kfAaeoH5DPrByiMA.tGA/eoxl9up1cVCeQXc99Xmw/IrPAXq','2021-08-16 05:05:27.748141','ab',1,2),
('SD0022','2021-08-09 21:45:30.018648','2000-01-02 00:00:00','Admin',true,true,false,'2021-08-09 00:00:00','Trong','$2a$10$O2jc8wbN9.uB2bKkFxji1.yXgR6TYvJ3hte8k0bpwGt1fprXv2usm','2021-08-18 16:44:00.049556','admint1',1,1),
('SD0066','2021-08-16 05:14:29.624134','2000-06-23 00:00:00','Oanh',false,false,false,'2021-08-11 00:00:00','Staff','$2a$10$u9nnx/7AdSCli1iaIcUwpe0Pifb.KnhSDf7Te1M45Bv88o2I4lcRu','2021-08-16 05:22:09.197736','oanhs',1,2),
('SD0062','2021-08-16 05:03:41.976448','2001-02-08 00:00:00','staff',true,true,true,'2021-08-04 00:00:00','account','$2a$10$1N6Kk5ITFt1dfciNeC/S7uEFYu1IHdUMY7B.VnCQA6aAg7T7zHYTe','2021-08-16 05:03:41.976461','staffa',1,2);
INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0069','2021-08-16 05:18:57.871826','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$EZFVGZBWlPPmdOB3N6C5A.C1hGuk7HpZOHkJ8Q36rJTrwgmDM8hFS','2021-08-16 05:18:57.871838','haot5',1,1),
('SD0068','2021-08-16 05:17:14.454961','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$bj15HWYFneAt.4MEVjnMVebRonwD9G/882QMNgBQ.ETaNwUjYM13q','2021-08-16 05:17:14.454974','haot4',1,1),
('SD0063','2021-08-16 05:13:00.917047','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$miIMXcUbSB1Wgg6aiyiS4OKt2mdiZLM4S60c.Jh8mced.xDqjbN1K','2021-08-16 05:13:00.91706','haot',1,1),
('SD0071','2021-08-16 07:00:52.804061','1923-02-01 00:00:00','Elon',true,false,true,'2021-07-28 00:00:00','Musk','$2a$10$34vQQ58LnlVjpt.4L9jF2OoGU9GCL6BZMeniGmXLoEs8s1brvbtRi','2021-08-16 07:00:52.804075','elonm',2,2),
('SD0067','2021-08-16 05:14:53.534588','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$OSoAmLg5tfARrQs9ogWNz.JURORxeWGzIaXo/LOJLra0C3bL.QURi','2021-08-16 05:14:53.534601','haot3',1,1),
('SD0058','2021-08-16 04:11:57.331676','2000-01-02 00:00:00','Admin',true,false,false,'2021-08-17 00:00:00','Test HCM','$2a$10$HTpi7Hu1RWK.HDitxDHrj.WBplmUyzfSUpD27EOm5N0SnPGux8PSq','2021-08-16 18:27:03.096454','adminth',1,1),
('SD0070','2021-08-16 06:22:40.823605','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$ZTEd4R00FXwoJ0kd5njllOM8cUtH7jyd6yOMKKQjbOVWMnG7Ln4qq','2021-08-16 06:22:40.823617','haot6',1,1),
('SD0065','2021-08-16 05:14:05.13304','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$tNX0ii7SyHV.QAaSqjij8.4cwPj7TljB.Khlk6qTcBf/9QmrybnVm','2021-08-16 05:14:05.133079','haot2',1,1);
INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0064','2021-08-16 05:13:31.791235','1999-12-31 00:00:00','Hao',false,true,true,'2000-01-03 00:00:00','Truong','$2a$10$d5XtlSGp7FdyHnWLGoVneeY/AFA9ndqH2UGUbDxkxaPTXSkgPCwlm','2021-08-16 06:26:19.992015','haot1',1,1),
('SD0076','2021-08-17 03:33:48.118771','2000-02-03 00:00:00','Nguyen',true,false,true,'2021-08-19 00:00:00','Trong','$2a$10$BqARAFS0DCFTzOm60JcQ3O59fCsoXZXaEvYer3q.xmA7MDcAZKZMW','2021-08-17 03:33:48.118831','nguyent',1,1),
('SD0075','2021-08-17 02:58:59.795253','2000-01-01 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$hZxlgeMemORiQCDK2jwJU.Av.PYKF47iX.S1Ju/IYjuwSNcxB/5ra','2021-08-18 02:19:25.867415','haot10',1,1),
('SD0074','2021-08-17 02:56:39.122236','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$d51t4WuPI04bbFjvDXmvfu6hrrUmd0LdAHNWxYMsgYenFcYKKoz8G','2021-08-17 02:56:39.122249','haot9',1,1),
('SD0073','2021-08-17 02:25:29.322692','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$YiAKzaSwSUjbkK.xOyEO2eq/14oMk8nw6frT7eu2JhQPgVs76lBde','2021-08-17 02:25:29.322706','haot8',1,1),
('SD0061','2021-08-16 05:02:24.716437','2000-05-20 00:00:00','Admin',false,false,false,'2021-08-12 00:00:00','Admin Account','$2a$10$c5MsjJggwIvpQr8W8oPWr.N3nOhsng2nYja0mgOtiA.7hIaPQ4anm','2021-08-19 02:17:50.999732','adminaa',1,1),
('SD0077','2021-08-17 03:50:01.828063','2000-01-02 00:00:00','Nguyen',true,false,true,'2021-08-18 00:00:00','Cuong','$2a$10$SyuC1P.fJ1LQRhIzrC6xYO9EM2/sO9FMqWgJmZvungagJsz0BvoBy','2021-08-17 03:50:01.828075','nguyenc',1,1);
INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0078','2021-08-17 03:58:33.402057','2000-10-15 00:00:00','Oanh',false,false,true,'2021-08-03 00:00:00','Pham Thi Kieu','$2a$10$VUXF7LklI901Itxh5j.O6uMRm7OPSFeRWpr5yTKsX0LcvfexxIfdq','2021-08-17 03:58:33.402068','oanhptk',1,2),
('SD0080','2021-08-17 09:11:24.48502','2000-02-06 00:00:00','staff',false,false,true,'2021-08-18 00:00:00','HN','$2a$10$e5C.J1ad.yiPNyzLiTSSeOt2wY3dczJw32K.DhR3PD6Jewfb4wjRO','2021-08-17 09:11:24.485037','staffh',2,2),
('SD0082','2021-08-18 05:57:50.999294','1999-11-02 00:00:00','John',true,false,true,'2021-06-21 00:00:00','Cena','$2a$10$TjYn6b8VlHaUyO0.DtMRTOyk8SBHo/rYDk46NRzkScoQaxN3qmTqK','2021-08-18 05:57:50.999322','johnc',1,1),
('SD0083','2021-08-18 06:02:51.467897','2000-04-05 00:00:00','Nguyen',true,false,true,'2021-08-18 00:00:00','Thanh Tin','$2a$10$.LCsC6m/hZqQIJn6k2ShFOAwPVQoiIM30G6Rc0W7JbInjGzzSGeSG','2021-08-18 06:02:51.467907','nguyentt',1,2),
('SD0072','2021-08-17 02:17:50.0223','2000-01-01 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$4l8cPRlabYpv6ebQzygXHu72q314SCrJJ3.Hkx2sx0LIB.MW7lF8K','2021-08-18 02:18:35.64242','haot7',1,1),
('SD0095','2021-08-19 14:39:15.307075','2000-02-01 00:00:00','Hai',true,false,false,'2021-08-13 00:00:00','Staff','$2a$10$LaBk9kG9q0EbMcJlFkIQFuq2cVner26bth91AxqJDT3mzQK3Zgi8.','2021-08-19 14:42:23.9657','hais',1,2),
('SD0085','2021-08-18 06:49:15.98005','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$d355dLZd3qv1lzcXCBY7WeLAvp9isFS7I.g.NQyef/hH7DPBQyqI2','2021-08-18 06:49:15.980057','haot12',1,1),
('SD0087','2021-08-18 07:08:36.910591','1999-12-31 00:00:00','Hao',true,false,true,'2021-06-22 00:00:00','Truong','$2a$10$k0pDOOAcJ3rhKSt2QNUhO.dAYMILoKeRNFc8mxJpJaN/UVIVvF9ty','2021-08-18 07:08:36.910599','haot14',1,1),
('SD0079','2021-08-17 03:59:31.366192','1999-11-02 00:00:00','Hai',true,false,false,'2021-08-17 00:00:00','Pham Minh','$2a$10$7YDa/7RLo54.SssSTiyWXuyjQxUo02s/osuuI9nmWyXPmQdMs4uNq','2021-08-19 14:43:21.941127','haipm',1,1),
('SD0084','2021-08-18 06:20:53.561705','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$mE3/Wi8O13AoCSLtFLq7vu8jaxEVDE8HlTEFvrxnXtCPC8vWitovC','2021-08-18 06:20:53.561714','haot11',1,1);
INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0088','2021-08-18 13:21:15.679131','1999-12-31 00:00:00','Hao',true,false,true,'2021-06-22 00:00:00','Truong','$2a$10$wiSzEOALtaSbEde3kbUQg.e9Dx7BbsA4dsXUmm/Py0eD/.9u7v2J2','2021-08-18 13:21:15.679142','haot15',1,1),
('SD0086','2021-08-18 06:52:00.873209','1999-12-31 00:00:00','Hao',true,true,true,'2021-06-22 00:00:00','Truong','$2a$10$2greQj.yBeseZbzcLLIrp.VwU/jOC32KTIGUaDKo3IIOKXDPBkf4i','2021-08-18 06:52:00.873216','haot13',1,1),
('SD0089','2021-08-18 15:30:21.744504','2000-04-05 00:00:00','Nguyen',true,false,false,'2021-08-18 00:00:00','Thanh Tin','$2a$10$Nx8aKzdB2aZlJp8S2oI7EOAzc3QZoUu1lJSBLyHwvEYXx0c60O/8G','2021-08-18 15:32:47.123206','nguyentt1',1,2),
('SD0090','2021-08-19 06:52:35.888665','2000-05-18 00:00:00','test',false,false,false,'2021-08-11 00:00:00','first assignment','$2a$10$LNJ9vVp3YTdhxdXBoZZDgOoqYav.lggdPrOdAhPhQ1dVpWEouAvQO','2021-08-19 07:34:01.094234','testfa',1,2),
('SD0092','2021-08-19 06:53:48.345048','2001-05-17 00:00:00','test',false,false,false,'2021-08-26 00:00:00','admin asm','$2a$10$RSD50jP5SYjsuKzzRrqjd.cWV8NmArZrpXYEtLClaFijjzcUQ6qe2','2021-08-19 07:36:33.315943','testaa',1,1),
('SD0091','2021-08-19 06:53:01.798006','2001-05-05 00:00:00','test',false,false,false,'2021-08-18 00:00:00','second assignment','$2a$10$E95OtYuEyY.P8CqdplQjxuiZoaAX0C4/2q1SvBt89k4EXlZOZ801e','2021-08-19 07:37:15.90738','testsa',1,2),
('SD0081','2021-08-18 05:49:53.939161','2000-05-03 00:00:00','staff',false,true,true,'2021-08-26 00:00:00','Admin Account','$2a$10$cGwWWOVZONkPrpE9tWK5R.FUDcDxo7LLrQqPMmYTQvHBsLkPNQYeK','2021-08-18 05:49:53.939176','staffaa',1,2),
('SD0096','2021-08-20 01:50:37.947878','2001-01-01 00:00:00','test',false,true,false,'2021-08-19 00:00:00','delete user','$2a$10$CCopeOcJAOTGyHTpR0p0eu1Qq/NYqOKFyXfknS5kfI.32xK5FHgc.','2021-08-20 01:51:21.648437','testdu',1,2),
('SD0093','2021-08-19 08:04:34.521367','2000-05-11 00:00:00','test',false,true,false,'2021-08-25 00:00:00','asm','$2a$10$p5VU1wS3hs/WKLsMYUS3Uu8ZgNoXgN.NnXHB6thLNT1t7KilEFTFC','2021-08-19 08:07:17.117282','testa',1,2),
('SD0094','2021-08-19 08:17:13.511497','1991-05-09 00:00:00','test',false,true,false,'2021-08-18 00:00:00','disable','$2a$10$JK8BiHvJuUySBMdnsLopt.04YXWQMAYLNkaD2Bak6f0dCAkxtvqrq','2021-08-19 08:18:13.712218','testd',1,1);
INSERT INTO public.users (code,created_date,date_of_birth,first_name,gender,is_deleted,is_new,joined_date,last_name,"password",updated_date,username,location_id,role_id) VALUES
('SD0097','2021-08-20 01:53:09.792481','2001-01-01 00:00:00','test',false,true,false,'2021-08-31 00:00:00','delete','$2a$10$TiVWFA0YkVxyGUgD84/Ye.Xr.xDC7/aay0iNQ4xx7RxH0DK0n/iGq','2021-08-20 01:54:10.173522','testd1',1,1),
('SD0098','2021-08-20 02:25:56.615246','2001-01-01 00:00:00','test',false,true,false,'2021-08-25 00:00:00','test','$2a$10$gzdmN5RZqjdpBQiOTCvcp.campGok.RNEApblqkQxexEZpLWJi8xW','2021-08-20 02:26:36.301418','testt',1,1),
('SD0099','2021-08-20 02:27:23.395621','2001-01-01 00:00:00','test',false,false,true,'2021-08-25 00:00:00','other delete','$2a$10$3eTh3NT3jMptiQJZy03.Lu8zwdqXjREAzqXKK8RA/wmV3A8oz3biC','2021-08-20 02:27:23.395632','testod',1,2);

INSERT INTO public.assets (id,asset_code,created_date,installed_date,is_deleted,"name",specification,updated_date,category_id,location_id,managed_by,state) VALUES
(10,'MN000001','2021-08-12 21:03:22.480824','2021-08-17 00:00:00',false,'Lenovo IdeaCentre AIO 3','Lenovo IdeaCentre AIO 3','2021-08-12 22:31:05.971866',4,1,'SD0022',1),
(11,'MN000002','2021-08-12 21:17:21.071826','2021-08-11 00:00:00',false,'iMac 21.5" 2020','iMac 21.5" 2020','2021-08-12 22:31:15.087908',4,1,'SD0022',5),
(17,'LT000001','2021-08-15 10:02:01.034854','2021-08-21 00:00:00',false,'MacBook Pro 16" 2019','Touch Bar 2.3GHz Core i9 1TB
16.0", 3072 x 1920 Pixel, IPS, IPS LCD LED Backlit, True Tone','2021-08-15 10:02:01.034854',7,1,'SD0022',3),
(18,'LT000002','2021-08-15 10:03:07.084507','2021-08-21 00:00:00',false,'MacBook Pro 16" 2019','Touch Bar 2.6GHz Core i7 512GB
16.0", 3072 x 1920 Pixel, IPS, IPS LCD LED Backlit, True Tone','2021-08-15 10:03:07.084507',7,1,'SD0022',3),
(19,'LT000003','2021-08-15 10:15:35.455977','2021-08-12 00:00:00',false,'MacBook Pro 13" 2020','Touch Bar 2.0GHz Core i5 512GB, 13.3", 2560 x 1600 Pixel, IPS, IPS LCD LED Backlit, True Tone','2021-08-15 10:15:35.455977',7,1,'SD0022',2),
(23,'TP000001','2021-08-15 10:18:31.19717','2021-08-03 00:00:00',false,'Microsoft Surface Pen','Surface Pen','2021-08-15 10:18:31.19717',8,1,'SD0022',2),
(24,'LT000006','2021-08-15 10:18:56.788682','2021-08-25 00:00:00',false,'Microsoft Surface Pro 7','i5 1035G4/8GB/128GB SSD/12.3" Touch/Win10','2021-08-15 10:18:56.788682',7,1,'SD0022',3),
(25,'LT000007','2021-08-15 10:20:33.767458','2021-08-24 00:00:00',false,'Asus Vivobook X515EA BQ993T','i5 1135G7/8GB/512GB SSD/Win10','2021-08-15 10:20:33.768456',7,1,'SD0022',3),
(2,'LT000010','2021-08-16 09:44:40.137177','2021-08-04 00:00:00',false,'Laptop Dell Vostro 5402','Intel Xeon 7749, RAM 128GB, SSD 10TB, Windows 20','2021-08-16 09:44:40.137204',7,2,'SD0059',2),
(1,'PC000001',NULL,'2021-08-17 00:00:00',false,'test pc2','test','2021-08-17 04:23:15.99959',11,1,'SD0061',2);
INSERT INTO public.assets (id,asset_code,created_date,installed_date,is_deleted,"name",specification,updated_date,category_id,location_id,managed_by,state) VALUES
(22,'LT000011','2021-08-17 05:39:12.734078','2021-08-26 00:00:00',false,'Laptop 1','test 1','2021-08-17 05:39:12.734104',7,1,'SD0061',2),
(27,'LT000012','2021-08-17 06:00:07.085626','2021-08-18 00:00:00',false,'Laptop test 2','OK','2021-08-17 06:00:07.085636',7,1,'SD0022',2),
(28,'LT000013','2021-08-17 06:00:38.234898','2021-08-25 00:00:00',false,'Laptop test 2','OK','2021-08-17 06:00:38.234908',7,1,'SD0022',2),
(29,'LT000014','2021-08-17 06:01:00.209081','2021-08-25 00:00:00',false,'Laptop test 1','OK','2021-08-17 06:01:00.209091',7,1,'SD0022',2),
(30,'TL000001','2021-08-17 07:18:47.372164','2021-08-04 00:00:00',false,'Samsung Tab 10','ram 500gb','2021-08-17 07:18:47.372196',9,1,'SD0058',2),
(40,'MN000003','2021-08-17 07:26:01.295152','2021-08-06 00:00:00',false,'Monitor 1','test monitor','2021-08-17 07:26:01.29517',4,1,'SD0061',3),
(41,'MN000004','2021-08-17 07:26:31.216607','2021-09-02 00:00:00',false,'Monitor 2','test 2','2021-08-17 07:26:31.216629',4,1,'SD0061',2),
(42,'IA000001','2021-08-17 09:01:30.974486','2021-09-01 00:00:00',false,'Ipad 1','test location','2021-08-17 09:01:30.974498',3,2,'SD0059',2),
(43,'MI000001','2021-08-17 09:07:42.843143','2021-08-17 00:00:00',false,'Mirco 1','test','2021-08-17 09:07:42.843155',15,2,'SD0059',2),
(44,'BL000004','2021-08-17 09:08:13.849647','2021-08-26 00:00:00',false,'Bluetooth test','test','2021-08-17 09:08:13.849657',1,2,'SD0059',3);
INSERT INTO public.assets (id,asset_code,created_date,installed_date,is_deleted,"name",specification,updated_date,category_id,location_id,managed_by,state) VALUES
(45,'KB000002','2021-08-17 09:09:13.893259','2021-09-02 00:00:00',false,'laptop test load','incorrect category','2021-08-17 09:09:13.893271',5,2,'SD0059',3),
(51,'TL000003','2021-08-18 06:08:07.939697','2021-08-19 00:00:00',false,'Samsung Tablet 3','Exyno rack, jack 3.5, ram 512mb','2021-08-18 06:08:07.939759',9,1,'SD0079',2),
(52,'TL000004','2021-08-18 06:09:13.534831','2021-08-16 00:00:00',false,'Samsung tablet 4','ram 1gb, chip snap 430','2021-08-18 06:09:13.53484',9,1,'SD0079',3),
(20,'LT000004','2021-08-15 10:16:25.437688','2021-08-11 00:00:00',false,'MacBook Air 13" 2020 M1','13.3", 2560 x 1600 Pixel, IPS, IPS LCD LED Backlit, True Tone','2021-08-18 14:53:59.643998',7,1,'SD0022',1),
(14,'BL000001','2021-08-14 17:15:43.932811','2021-08-26 00:00:00',false,'Bluetooth TP-Link UB40','V3.0/2.1/2.0/1.1 No Driver 1','2021-08-18 09:02:00.566465',1,1,'SD0022',5),
(53,'LT000019','2021-08-18 09:14:59.850629','2021-08-15 00:00:00',false,'Laptop Dell XPS 9981','Super Ultra book, ram 16gb, ssd 1tb, core i9','2021-08-18 09:14:59.850636',7,1,'SD0079',2),
(54,'MN000005','2021-08-18 09:15:54.804731','2021-08-26 00:00:00',false,'Monitor Dell V27K2','Màn hình 27 inch, tấm nền IPS, độ phân giải 2K','2021-08-18 09:15:54.804738',4,1,'SD0079',2),
(60,'HP000001','2021-08-18 14:45:00.006222','2021-08-17 00:00:00',false,'Apple AirPods 2','Tai nghe không dây bluetooth, khoảng cách 10m, pin 3 tiếng','2021-08-18 14:51:24.943952',6,1,'SD0079',3),
(50,'SW000002','2021-08-18 06:04:54.660252','2021-08-04 00:00:00',false,'Apple Watch Seri 4','Nhựa 38mm','2021-08-18 16:56:13.940762',19,1,'SD0079',1),
(34,'IP000001','2021-08-17 07:21:16.230038','2021-08-11 00:00:00',false,'Iphone 12','ram 2gb','2021-08-19 07:37:21.033283',2,1,'SD0058',1);
INSERT INTO public.assets (id,asset_code,created_date,installed_date,is_deleted,"name",specification,updated_date,category_id,location_id,managed_by,state) VALUES
(31,'TL000002','2021-08-17 07:19:02.724305','2021-08-07 00:00:00',false,'Samsung Tab 11','zxadesa','2021-08-20 19:49:55.048524',9,1,'SD0058',6),
(33,'LT000016','2021-08-17 07:21:15.42049','2021-08-19 00:00:00',false,'Laptop 1','OK','2021-08-20 02:28:52.058522',7,1,'SD0022',6),
(37,'LT000018','2021-08-17 07:21:45.397024','2021-08-25 00:00:00',false,'Laptop 2','OK','2021-08-19 06:58:02.955176',7,1,'SD0022',6),
(13,'KB000001','2021-08-13 14:43:18.490752','2021-08-04 00:00:00',false,'Keyboard1','Ko biet','2021-08-20 06:48:49.033626',5,1,'SD0022',1),
(49,'SW000001','2021-08-18 06:04:21.811836','2021-08-17 00:00:00',false,'Apple Watch Seri 6','Thép, 44 mm','2021-08-19 07:34:11.619262',19,1,'SD0079',1),
(38,'BL000002','2021-08-17 07:21:55.853136','2021-08-26 00:00:00',false,'Laptop 1','test n','2021-08-20 20:16:44.088299',1,1,'SD0061',2),
(39,'BL000003','2021-08-17 07:22:12.329276','2021-08-27 00:00:00',false,'Laptop 2','test','2021-08-20 06:49:13.886923',1,1,'SD0061',2),
(21,'LT000005','2021-08-15 10:17:11.05586','2021-08-03 00:00:00',false,'Microsoft Surface Pro 7','i7 1065G7/16GB/256GB SSD/12.3"Touch/Win 10','2021-08-19 09:46:08.685079',7,1,'SD0022',1),
(32,'LT000015','2021-08-17 07:21:08.509134','2021-08-19 00:00:00',false,'Laptop 1 test','test','2021-08-20 19:26:16.116993',7,1,'SD0061',6),
(35,'LT000017','2021-08-17 07:21:22.909187','2021-09-02 00:00:00',false,'Laptop 2 test','test 2','2021-08-20 19:27:40.769526',7,1,'SD0061',6);
INSERT INTO public.assets (id,asset_code,created_date,installed_date,is_deleted,"name",specification,updated_date,category_id,location_id,managed_by,state) VALUES
(36,'IP000002','2021-08-17 07:21:35.767246','2021-08-16 00:00:00',false,'@!#@!#','ram 5gb','2021-08-20 20:16:44.091022',2,1,'SD0058',6),
(76,'TL000005','2021-08-20 21:04:30.069946','2021-08-18 00:00:00',false,'Samsung Tab 1','asddsa','2021-08-20 21:04:30.069959',9,1,'SD0079',2),
(26,'LT000008','2021-08-15 10:21:05.341135','2021-08-04 00:00:00',false,'Asus Zenbook UX425EA KI439T','i7 1165G7/16GB/512GB SSD/Win 10','2021-08-21 09:13:31.982001',7,1,'SD0022',2);

INSERT INTO public.assignments (id,assigned_date,is_deleted,note,updated_date,asset_id,assigned_by,assigned_to,state) VALUES
(58,'2021-08-18 00:00:00',false,'HiHi','2021-08-18 14:41:37.926376',1,'SD0079','SD0082',2),
(61,'2021-08-18 00:00:00',false,'Giao cho be Oanh 99','2021-08-18 14:45:50.61834',60,'SD0079','SD0078',2),
(62,'2021-08-18 00:00:00',false,'OK','2021-08-18 16:56:13.937276',50,'SD0022','SD0058',1),
(63,'2021-08-18 00:00:00',true,'ok','2021-08-18 16:59:21.247218',26,'SD0022','SD0058',3),
(65,'2021-08-19 00:00:00',false,'test 1','2021-08-19 07:34:11.616202',49,'SD0061','SD0090',1),
(66,'2021-08-19 00:00:00',false,'test with 2 asm','2021-08-19 07:37:21.031404',34,'SD0061','SD0091',1),
(71,'2021-08-19 00:00:00',false,'test delete','2021-08-19 09:46:08.682954',21,'SD0061','SD0091',1),
(72,'2021-08-20 00:00:00',false,'this admin is deleted','2021-08-20 02:28:52.048052',33,'SD0098','SD0099',2),
(67,'2021-09-01 00:00:00',false,'test','2021-08-20 03:32:41.299494',37,'SD0061','SD0091',2);
INSERT INTO public.assignments (id,assigned_date,is_deleted,note,updated_date,asset_id,assigned_by,assigned_to,state) VALUES
(64,'2021-08-19 00:00:00',false,'xc','2021-08-20 06:48:48.985001',13,'SD0079','SD0061',1),
(69,'2021-08-19 00:00:00',true,'xcz','2021-08-20 06:49:13.88474',39,'SD0079','SD0061',3),
(73,'2021-08-21 00:00:00',false,'','2021-08-20 19:26:16.074614',32,'SD0079','SD0058',2),
(70,'2021-08-19 00:00:00',false,'','2021-08-20 19:27:40.763208',35,'SD0079','SD0032',2),
(75,'2021-08-21 00:00:00',false,'cv','2021-08-20 19:49:55.045664',31,'SD0079','SD0078',2),
(47,'2021-08-26 00:00:00',false,'Accept faster','2021-08-20 20:14:02.674455',20,'SD0022','SD0089',2),
(74,'2021-08-21 00:00:00',false,'jnk','2021-08-20 20:16:44.086327',36,'SD0079','SD0033',2),
(57,'2021-09-05 00:00:00',false,'HiHikjkhjh ihkj  2312312123!##@!#@!#','2021-08-20 20:19:09.575565',49,'SD0079','SD0082',2),
(68,'2021-08-19 00:00:00',true,'test','2021-08-19 07:34:25.723934',26,'SD0061','SD0090',1);

INSERT INTO public.return_request (id,assigned_date,created_date,is_deleted,returned_date,state,updated_date,accept_by,asset_id,assignment_id,request_by) VALUES
(77,'2021-08-19 00:00:00','2021-08-21 09:13:21.961352',false,'2021-08-21 00:00:00',true,'2021-08-21 09:13:32.034462','SD0058',26,68,'SD0058');

INSERT INTO public.historical_assignments (id,assigned_date,returned_date,asset_id,assigned_by,assigned_to) VALUES
(78,'2021-08-19 00:00:00','2021-08-21 00:00:00',26,'SD0061','SD0090');