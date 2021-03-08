create table fwk_transaction_hst
(
    transaction_date date                                       not null,
    app_name         varchar(20)                                not null,
    app_version      varchar(20)                                not null,
    gid              varchar(255)                               not null,
    "method"         varchar(6),
    "path"           varchar(2000),
    status_code      varchar(3),
    start_time       varchar(6),
    end_time         varchar(6),
    elapsed          varchar(11),
    api_id           varchar(9),
    host_name        varchar(50),
    remote_ip        varchar(20),
    query_string     text,
    body             text,
    error_message    varchar(2000),
    referrer         varchar(200),
    mob_yn           varchar(1)  default 'N'::character varying not null,
    create_user_id   varchar(11),
    create_dt        timestamptz default current_timestamp      not null,
    update_user_id   varchar(11),
    update_dt        timestamptz
);

create index idx_fwk_transaction_hst on fwk_transaction_hst (transaction_date, app_name, app_version, gid);

comment on table fwk_transaction_hst is '거래내역';

comment on column fwk_transaction_hst.transaction_date is '거래 일자';

comment on column fwk_transaction_hst.app_name is '어플리케이션 명';

comment on column fwk_transaction_hst.app_version is '어플리케이션 버전';

comment on column fwk_transaction_hst.gid is '글로벌 ID';

comment on column fwk_transaction_hst."method" is 'Http Method [GET,POST...]';

comment on column fwk_transaction_hst."path" is '요청 경로';

comment on column fwk_transaction_hst.status_code is '응답 코드';

comment on column fwk_transaction_hst.start_time is '시작 시간';

comment on column fwk_transaction_hst.end_time is '종료 시간';

comment on column fwk_transaction_hst.elapsed is '경과 시간';

comment on column fwk_transaction_hst.api_id is 'API ID';

comment on column fwk_transaction_hst.host_name is '호스트 명';

comment on column fwk_transaction_hst.remote_ip is '요청지 IP';

comment on column fwk_transaction_hst.query_string is '요청 파라미터';

comment on column fwk_transaction_hst.body is '요청 바디';

comment on column fwk_transaction_hst.error_message is '에러 메시지';

comment on column fwk_transaction_hst.mob_yn is '모바일 여부';

comment on column fwk_transaction_hst.create_user_id is '생성자 ID';

comment on column fwk_transaction_hst.create_dt is '생성 일시';

comment on column fwk_transaction_hst.update_user_id is '수정자 ID';

comment on column fwk_transaction_hst.update_dt is '수정 일시';