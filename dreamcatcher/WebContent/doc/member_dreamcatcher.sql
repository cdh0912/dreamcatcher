SELECT * FROM member;

DELETE FROM member;                     

---------------- 관리자 회원가입 --------------------
 

INSERT INTO member(id, password, name, m_level, m_state)
VALUES('dreamcatchul@gmail.com', '12345', '드림캐처', 1, 1); 


-----------------일반 회원가입 ----------------------- 

INSERT INTO member(id, password, name, m_level, m_state)
VALUES('admin@dreamcatcher.com', 'asdf1234', '드림캐처', 0, 0); 


---------------- 아이디 중복 검사 --------------------
 
SELECT COUNT(id) FROM member
WHERE id = 'admin@dreamcatcher.com';

---------------- 회원가입 승인  --------------------

UPDATE member
SET m_state = 1
WHERE id = 'aaaaa';

---------------- 비밀번호 변경 --------------------
UPDATE member
SET password = 'aaaaa'
WHERE id = 'aaaaa';

---------------- 로그인 ------------------------ 
SELECT id, name, m_level, m_state
FROM member
WHERE id = ? AND password = ?

---------------- 회원정보 수정 ---------------------------- 
UPDATE member
SET name = ?, password = ?
WHERE id = ?
			
---------------	 비밀번호 리셋 및 회원상태 변경 -------------
UPDATE member
SET password = ?, m_state = ?
WHERE id = ?
			
---------------- 패스워드 검사 ----------------------
SELECT COUNT(id)
FROM member
WHERE id = ? AND password = ?

COMMIT;