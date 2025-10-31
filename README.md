# みんチケ 課題管理（SQLite + MyBatis 版）

Spring Boot + Thymeleaf + MyBatis（SQLite永続化）。IBM Cloud Code Engine へのデプロイに対応。

## 機能
- 課題の登録/更新/削除
- 課題一覧・簡易検索（フリーワード、ステータス）
- 添付ファイルの保存（/data/uploads）

## ローカル起動
- `mvn spring-boot:run`
- ブラウザで `http://localhost:8080`
- データ保存先: `/data/mintike.db`（環境変数 `APP_DATA_DIR` で変更可）

## ビルド
- `mvn -DskipTests package`

## Docker（ローカル）
- `docker build -t mintike-issues:latest .`
- `mkdir data`
- `docker run --rm -p 8080:8080 -e APP_DATA_DIR=/data -v %cd%/data:/data mintike-issues:latest`

## IBM Cloud Code Engine（概要）
1. コンテナをレジストリへPush
2. Code Engine でアプリ作成、上記イメージ指定
3. 環境変数 `APP_DATA_DIR=/data` を設定（必要なら永続ボリュームをアタッチ）
4. ポートは 8080（既定）

ライセンス: MIT


