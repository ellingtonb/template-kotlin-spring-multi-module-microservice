DO $$
 BEGIN
    IF EXISTS (SELECT rolname FROM pg_roles where rolname like 'template-kotlin-multi-module') THEN
	 SET ROLE "template-kotlin-multi-module";
	end if;
  end;
$$;

SET STATEMENT_TIMEOUT TO '300s';
