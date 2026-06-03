**Nguyên nhân gây ra lỗi `Invalid JWT` là do sử dụng sai Secret Key khi xác minh token**:

- Khi tạo JWT, hệ thống dùng một `Secret Key` để ký (`signWith(key, SignatureAlgorithm.HS256)`).
- Để xác minh JWT, hệ thống phải dùng chính xác cùng Secret Key đã dùng để ký.
- Trong đoạn code gốc, một key mới (`differentKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)`) được tạo ra và dùng để xác minh. Vì key này khác với key ban đầu, chữ ký không khớp → JWT bị coi là giả mạo → lỗi xác thực.