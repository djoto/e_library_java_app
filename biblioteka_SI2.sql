create database biblioteka_SI2;

set sql_safe_updates = 0;

use biblioteka_SI2;

create table if not exists TipDela(
    id_tipa int auto_increment primary key,
    naziv_tipa_dela varchar(100) not null
);

create table if not exists AutorskoDelo(
    id_dela int auto_increment primary key,
    id_tipa int not null,
    naziv_dela varchar(200) not null,
    vreme_izdanja datetime not null default CURRENT_TIMESTAMP,
    izdavac varchar(100) not null,
    ISBN_broj varchar(50),
    preview_putanja varchar(300),
    ukupna_kolicina int not null,
    trenutno_stanje int not null,
    foreign key(id_tipa) references TipDela(id_tipa) on update restrict on delete restrict
);

create table if not exists Poglavlje(
    id_poglavlja int auto_increment primary key,
    naziv_poglavlja varchar(200) not null,
    id_dela int not null,
    foreign key(id_dela) references AutorskoDelo(id_dela) on update restrict on delete restrict
);

create table if not exists VlasnikDela(
    id_vlasnika int auto_increment primary key,
    zvanje_ime_prezime varchar(100) not null,
    tip_vlasnika enum('autor', 'editor', 'mentor', 'autor_poglavlja') not null, 
    id_dela int,
    id_poglavlja int
);


create table if not exists Korisnik(
    id_korisnika int auto_increment primary key,
    ime_korisnika varchar(30) not null,
    prezime_korisnika varchar(30) not null,
    tip_korisnika enum('admin', 'bibliotekar', 'student') not null,
    korisnicko_ime varchar(30) not null,
    br_indeksa varchar(20) unique,
    e_mail varchar(50),
    clan_od datetime default CURRENT_TIMESTAMP,
    password_hash varchar(100) not null,
    prijavljen enum('Da', 'Ne') not null
);

create table if not exists Pozajmica(
    id_pozajmice int auto_increment primary key,
    id_studenta int not null,
    id_dela int not null,
    datum_uzimanja datetime not null default CURRENT_TIMESTAMP,
    datum_vracanja datetime default null,
    broj_pozajmljenih int not null,
    vraceno enum('Da', 'Ne'),
    foreign key(id_studenta) references Korisnik(id_korisnika) on update restrict on delete restrict,
    foreign key(id_dela) references AutorskoDelo(id_dela) on update restrict on delete restrict
);

create table if not exists Rezervacija(
    id_rezervacije int auto_increment primary key,
    id_studenta int not null,
    id_dela int not null,
    vreme_rezervisanja datetime not null default CURRENT_TIMESTAMP,
    broj_rezervisanih int not null,
    foreign key(id_studenta) references Korisnik(id_korisnika) on update restrict on delete restrict,
    foreign key(id_dela) references AutorskoDelo(id_dela) on update restrict on delete restrict
);

create table if not exists Obavestenje(
    id_obavestenja int auto_increment primary key,
    tip_obavestenja varchar(100) not null,
    id_pozajmice int,
    pregledao_student enum('Da', 'Ne') not null,
    pregledao_bibliotekar enum('Da','Ne') not null
);

create table if not exists ZahtevZaRegistraciju(
    id_zahteva int auto_increment primary key,
    ime_studenta varchar(30) not null,
    prezime_studenta varchar(30) not null,
    korisnicko_ime_studenta varchar(30) not null,
    password_hash_studenta varchar(100) not null,
    br_indeksa_studenta varchar(20) not null,
    e_mail_studenta varchar(50)
);


insert into TipDela(naziv_tipa_dela) values ('Udžbenik');
insert into TipDela(naziv_tipa_dela) values ('Zbirka zadataka');
insert into TipDela(naziv_tipa_dela) values ('Praktikum');
insert into TipDela(naziv_tipa_dela) values ('Monografija');
insert into TipDela(naziv_tipa_dela) values ('Završni rad');
insert into TipDela(naziv_tipa_dela) values ('Diplomski rad');
insert into TipDela(naziv_tipa_dela) values ('Master rad');
insert into TipDela(naziv_tipa_dela) values ('Doktorska disertacija');


insert into AutorskoDelo(id_tipa, naziv_dela, izdavac, ISBN_broj, preview_putanja, ukupna_kolicina, trenutno_stanje)
    values(1, 'Industrijski dizajn', 'Fakultet inženjerskih nauka Univerziteta u Kragujevcu', null, 'preview_images/Industrijski_dizajn.jpg', 3, 2);
insert into AutorskoDelo(id_tipa, naziv_dela, izdavac, ISBN_broj, preview_putanja, ukupna_kolicina, trenutno_stanje)
    values(1, 'Kompozitne konstrukcije', 'Fakultet inženjerskih nauka Univerziteta u Kragujevcu', '978‐86‐6335‐048‐9', 'preview_images/Kompozitne konstrukcije.jpg', 5, 3);
insert into AutorskoDelo(id_tipa, naziv_dela, izdavac, ISBN_broj, preview_putanja, ukupna_kolicina, trenutno_stanje)
    values(4, 'Osnovi bioinženjeringa', 'FIN Kragujevac', '978‐86‐8923‐048‐4', 'preview_images/osnovi_bioinzenjeringa.jpg', 7, 7);
    
    
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Lozica Ivanović', 'autor', 1, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Dr Siniša Kuzmanović', 'autor', 1, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Miroslav Vereš', 'autor', 1, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Milan Rackov', 'autor', 1, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Biljana Marković', 'autor', 1, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Zorica Đorđević', 'autor', 2, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Rektor Nenad Filipović', 'editor', 3, null);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Vučko Vučković', 'autor_poglavlja', null, 2);
insert into VlasnikDela(zvanje_ime_prezime, tip_vlasnika, id_dela, id_poglavlja)
    values('Rektor Nenad Filipović, Jasna Popović', 'autor_poglavlja', null, 1);

    
    
insert into Poglavlje(naziv_poglavlja, id_dela)
    values('Poglavlje 1', 3);
insert into Poglavlje(naziv_poglavlja, id_dela)
    values('Poglavlje 2', 3);

    

insert into Korisnik(ime_korisnika, prezime_korisnika, tip_korisnika, korisnicko_ime, br_indeksa, e_mail, password_hash, prijavljen)
    values('Stefan', 'Stefanović', 'student', 'stefanX2', '333/2021', 'stefo@gmail.com', '0d80da802094e4abd5ed8612c725faf64f70f471c2e26778ff91a6a3e4c88597', 'Ne');
insert into Korisnik(ime_korisnika, prezime_korisnika, tip_korisnika, korisnicko_ime, br_indeksa, e_mail, password_hash, prijavljen)
    values('Petar', 'Petrović', 'student', 'petarX2', '222/2021', 'petar@gmail.com', '48553a6182b00cc25eae07d9e86b689e55147c4d3886b127ddcf016cc8aa65e0', 'Ne');
insert into Korisnik(ime_korisnika, prezime_korisnika, tip_korisnika, korisnicko_ime, e_mail, password_hash, prijavljen)
    values('Milan', 'Milanović', 'bibliotekar', 'milanX2', 'milan@gmail.com', 'ec3ae6550b9446364138461d6a682d09d101ac1a5ed8a9298e0a50869fd832f7', 'Ne');
insert into Korisnik(ime_korisnika, prezime_korisnika, tip_korisnika, korisnicko_ime, e_mail, password_hash, prijavljen)
    values('Janko', 'Janković', 'admin', 'jankoX2', 'janko@gmail.com', '13ad9de19191b1a646c188c5b019f1695a3b7cd9a6e2f2cb6faba460fe5dc873', 'Ne');
    

insert into Pozajmica(id_studenta, id_dela, broj_pozajmljenih, vraceno)
    values(1,2,1, 'Ne');
insert into Pozajmica(id_studenta, id_dela, broj_pozajmljenih, vraceno)
    values(1,1,1, 'Ne');
    

insert into Rezervacija(id_studenta, id_dela, broj_rezervisanih)
    values(2,2,1);
    

insert into ZahtevZaRegistraciju(ime_studenta, prezime_studenta, korisnicko_ime_studenta, password_hash_studenta, br_indeksa_studenta, e_mail_studenta)
    values('Marko', 'Marković', 'markoX2', '48cf6c91c1d1eb864e1d7d6d38e65e6c02249083aca96740ba82f2f123a18177', '111/2020', 'marko@gmail.com');
    
    
insert into Obavestenje(tip_obavestenja, id_pozajmice, pregledao_student, pregledao_bibliotekar)
    values('Istek roka za vraćanje', 1, 'Ne', 'Ne');
