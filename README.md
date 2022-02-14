# Man10RespawnAnchor
Respawn地点を固定するためのプラグインです。
使用する際はWarpSystemなどのこのプラグインより高い権限で動作する他のリスポーン固定機能は切ってください。

## Commands
### /mspawn help
コマンド一覧を表示します。

### /mspawn [on/off]
リスポーンプラグインのon/offを設定します。

### /mspawn [joinon/joinoff]
サーバー入室時のデフォルトスポーン地点へのテレポートのon/offを設定します。

### /mspawn cpena [true/false]
コマンドでリスポーンしたときのデスペナルティの有無を設定します

### /mspawn dpena [true/false]
死亡時のデスペナルティの有無を設定します

### /mspawn respawn 
リスポーンします。今いるワールドに固有のスポーン地点が設定されている場合そちらに飛びます。

### /mspawn respawn 
指定したユーザーをリスポーンします。指定したユーザーが今いるワールドに固有のスポーン地点が設定されている場合そちらに飛びます。

### /mspawn set
mspawnのデフォルトスポーン地点を現在地にセットします。

### /mspawn set world
現在いるワールド固有のスポーン地点を現在地にセットします。

### /mspawn sethealth [数字]
リスポーン時の体力を設定します。1~20が設定可能です。

### /mspawn setfood [数字]
リスポーン時の満腹度を設定します。0~20が設定可能です。

### /mspawn message [text]
リスポーン時のメッセージを設定します。100文字まで設定可能です。

### /mspawn delete world
現在いるワールド固有のスポーン地点を削除します。

### /mspawn set world [ワールド名]
指定したワールド固有のスポーン地点を現在地にセットします。

### /mspawn delete world [ワールド名]
指定したワールド固有のスポーン地点を削除します。

### /mspawn exception add [ユーザー名]
指定したユーザーをこのプラグインのリスポーン機能の対象から外します。

### /mspawn exception delete [ユーザー名]
指定したユーザーをこのプラグインのリスポーン機能の対象にします。

### /mspawn exception add [ユーザー名]
指定したワールドをこのプラグインのリスポーン機能の対象から外します。

### /mspawn exception delete [ユーザー名]
指定したワールドをこのプラグインのリスポーン機能の対象にします。
