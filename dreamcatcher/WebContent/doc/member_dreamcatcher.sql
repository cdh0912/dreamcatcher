SELECT * FROM member;

DELETE FROM member;                     

---------------- ������ ȸ������ --------------------
 

INSERT INTO member(id, password, name, m_level, m_state)
VALUES('dreamcatchul@gmail.com', '12345', '�帲ĳó', 1, 1); 


-----------------�Ϲ� ȸ������ ----------------------- 

INSERT INTO member(id, password, name, m_level, m_state)
VALUES('admin@dreamcatcher.com', 'asdf1234', '�帲ĳó', 0, 0); 


---------------- ���̵� �ߺ� �˻� --------------------
 
SELECT COUNT(id) FROM member
WHERE id = 'admin@dreamcatcher.com';

---------------- ȸ������ ����  --------------------

UPDATE member
SET m_state = 1
WHERE id = 'aaaaa';

---------------- ��й�ȣ ���� --------------------
UPDATE member
SET password = 'aaaaa'
WHERE id = 'aaaaa';

---------------- �α��� ------------------------ 
SELECT id, name, m_level, m_state
FROM member
WHERE id = ? AND password = ?

---------------- ȸ������ ���� ---------------------------- 
UPDATE member
SET name = ?, password = ?
WHERE id = ?
			
---------------	 ��й�ȣ ���� �� ȸ������ ���� -------------
UPDATE member
SET password = ?, m_state = ?
WHERE id = ?
			
---------------- �н����� �˻� ----------------------
SELECT COUNT(id)
FROM member
WHERE id = ? AND password = ?

COMMIT;