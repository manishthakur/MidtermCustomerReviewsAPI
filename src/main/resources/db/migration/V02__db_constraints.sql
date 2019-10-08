alter table review
  add constraint fk_product_id
foreign key (product_id)
references product (id);

alter table comment
  add constraint fk_review_id
foreign key (review_id)
references review (id);
