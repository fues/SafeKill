# SafeKill

spigot用プラグイン

kill @e コマンドによる誤爆を防ぎます。

## ブロック対象コマンド

@eのセレクタに以下の引数が1つも含まれていない場合コマンドが実行されません

- sort と limit 両方
- distance
- tag
- name
- dx
- dy
- dz
- score

## 回避

`unsafekill`コマンドを使用することで強制的にkillコマンドを実行します。

また現在`execute as @e run kill @s`のような間接指定のコマンドは回避していません。