ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (check >= 11);
ALTER TABLE student ALTER COLUMN SET NOT NUll;
ALTER TABLE student ADD CONSTRAINT name_unique UNIQUE (name);
ALTER TABLE faculty ADD CONSTRAINT name_color_unique UNIQUE (name, color);
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
