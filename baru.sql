CREATE TABLE nilai_mahasiswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(15) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    nilai_tugas DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_uts DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_uas DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_akhir  DECIMAL(5,2) GENERATED ALWAYS AS (
        (nilai_tugas * 0.30) + (nilai_uts * 0.30) + (nilai_uas * 0.40)
    ) STORED,
    grade VARCHAR(3) GENERATED ALWAYS AS (
      CASE
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 87 THEN 'A'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 78 THEN 'B+'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 70 THEN 'B'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 63 THEN 'C+'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 56 THEN 'C'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 45 THEN 'D'
                     ELSE 'E'
                   END
                 ) STORED,

    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Validasi: nilai harus antara 0 dan 100
    CONSTRAINT chk_tugas CHECK (nilai_tugas BETWEEN 0 AND 100),
    CONSTRAINT chk_uts   CHECK (nilai_uts   BETWEEN 0 AND 100),
    CONSTRAINT chk_uas   CHECK (nilai_uas   BETWEEN 0 AND 100),

    UNIQUE KEY uk_nim (nim)
);