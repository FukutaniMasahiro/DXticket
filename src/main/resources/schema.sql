-- Issues main table
CREATE TABLE IF NOT EXISTS issues (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  issue_date TEXT NOT NULL,
  status TEXT NOT NULL,
  troubled_person TEXT,
  idea_person TEXT,
  support_members TEXT,
  title TEXT NOT NULL,
  target_due_date TEXT,
  current_problem TEXT,
  risk_if_unresolved TEXT,
  target_departments TEXT,
  action_details TEXT,
  memo TEXT,
  completed_date TEXT,
  effect_after_execution TEXT,
  reusability TEXT,
  created_at TEXT DEFAULT (datetime('now')),
  updated_at TEXT DEFAULT (datetime('now'))
);

-- Category master (fixed values)
CREATE TABLE IF NOT EXISTS categories (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL UNIQUE
);

-- Issue-Category mapping (many-to-many)
CREATE TABLE IF NOT EXISTS issue_categories (
  issue_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,
  PRIMARY KEY (issue_id, category_id),
  FOREIGN KEY (issue_id) REFERENCES issues(id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT
);

-- Attachments
CREATE TABLE IF NOT EXISTS attachments (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  issue_id INTEGER NOT NULL,
  original_name TEXT NOT NULL,
  stored_path TEXT NOT NULL,
  content_type TEXT,
  size_bytes INTEGER,
  uploaded_at TEXT DEFAULT (datetime('now')),
  FOREIGN KEY (issue_id) REFERENCES issues(id) ON DELETE CASCADE
);

-- Seed categories (id is stable for checkbox values)
INSERT OR IGNORE INTO categories(id, name) VALUES
  (1, 'Fieiホールディングス内部'),
  (2, 'Fieiホールディングス外部'),
  (3, 'FBI内部'),
  (4, 'FBI外部'),
  (5, 'KM内部'),
  (6, 'KM外部'),
  (7, 'サンリッチ内部'),
  (8, 'サンリッチ外部');


