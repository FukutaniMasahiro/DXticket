# みんチケ 課題管理（CSV版）

Spring Boot + Thymeleaf の軽量な課題登録/検索アプリ（DB不要・CSV保存）。IBM Cloud Code Engine へのデプロイに対応。

## 機能
- 課題の登録/更新/削除
- 課題一覧・簡易検索（フリーワード、ステータス）
- 採番CSVにより連番ID自動付与

## ローカル起動
- `mvn spring-boot:run` を実行
- ブラウザで `http://localhost:8080` を開く
- CSV保存先: `./data/issues.csv`, `./data/sequence.csv`
- 保存先変更: 環境変数 `APP_DATA_DIR` を設定

## ビルド
- `mvn -DskipTests package`

## Docker（ローカル）
- `docker build -t mintike-issues:latest .`
- `mkdir data`
- `docker run --rm -p 8080:8080 -e APP_DATA_DIR=/data -v %cd%/data:/data mintike-issues:latest`

## IBM Cloud Code Engine（概要）
1. コンテナをレジストリへPush（IBM CR など）
2. Code Engine でアプリ作成、上記イメージ指定
3. 環境変数 `APP_DATA_DIR=/data` を設定（必要なら永続ボリュームをアタッチ）
4. ポートは 8080（既定）

ライセンス: MIT


