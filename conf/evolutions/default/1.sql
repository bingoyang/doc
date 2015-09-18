# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `admin_user` (`id` INTEGER AUTO_INCREMENT PRIMARY KEY,`username` VARCHAR(254) NOT NULL,`password` VARCHAR(254) NOT NULL);

# --- !Downs

drop table `admin_user`;

