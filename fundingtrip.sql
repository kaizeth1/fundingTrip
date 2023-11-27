drop table if exists member;
drop table if exists hisboard;
drop table if exists tboard;
drop table if exists qboard;
drop table if exists treply;
drop table if exists qreply;


create table if not exists member(
	mid VARCHAR(20) NOT NULL PRIMARY KEY,
    mpw varchar(20) not null,
    mph varchar(20) not null,
    memail varchar(20) not null,
    mgrade varchar(20),
    mpayment varchar(30)
);

create table if not exists hisboard(
	hisnum int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    hisname varchar(20) not null,
    hisloca varchar(40) not null,
    hispic varchar(30) not null,
    hisexplan varchar(5000),
    hisinfo varchar(2000),
    hissysname varchar(50)
);

create table if not exists tboard(
	tnum int auto_increment not null primary key,
    tmid varchar(20) not null,
    ttitile varchar(50) not null,
    tcontents varchar(5000) not null,
    tpeople int not null,
    tstart date not null,
    tend date not null,
    tstatus varchar(10) BINARY default '대기', 
    tpic varchar(30),
    tsysname varchar(50),
    constraint fk_tmid foreign key(tmid)
    references member(mid)
);

create table if not exists qboard(
	qnum int auto_increment not null primary key,
    qmid varchar(20) not null,
    qtitle varchar(50) not null,
    qcategory varchar(20) not null,
    qcontents varchar(5000),
    qdate DATETIME DEFAULT CURRENT_TIMESTAMP,
    constraint fk_qmid foreign key(qmid)
    references member(mid)
);

create table if not exists treply(
	trnum int auto_increment not null primary key,
    trmid varchar(20) not null,
    trcontents varchar(200),
    trdate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_trmid foreign key(trmid)
    references member(mid)
);

create table if not exists qreply(
	qrnum int auto_increment not null primary key,
    qrmid varchar(20) not null,
    qrcontents varchar(200),
    qrdate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_qrmid foreign key(qrmid)
    references member(mid)
);