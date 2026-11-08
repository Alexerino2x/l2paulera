CREATE TABLE IF NOT EXISTS `gm_audit` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `gm_name` varchar(45),
  `target` varchar(255),
  `type` varchar(20),
  `action` varchar(255),
  `param` varchar(255),
  `date` date,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

-- -----------------
-- global:
-- -----------------
-- gm_name: "account - char"
--   account: accountname of the acting GM
--   char   : charactername of the acting GM
-- target : "targetid - targetname" or "null"
--   targetid  : ObjectID of the target of the acting GM
--   targetname: (character-)Name of the target of the acting GM

-- -----------------
-- admincommand:
-- -----------------
-- type  : "admincommand"
-- action: "command"
--   command: entered gmcommand (beginning with admin_ )
-- param : "params"
--   params: entered params if any

-- -----------------
-- GM drop/petdrop:
-- -----------------
-- type  : "dropitem"
-- action: "process"
--   process: should explain why it is dropped
-- param : "(x,y,z) - count - enchant - itemid - itemname"
--   x,y,z   : position of the dropping GM (the drop itself might be in a different location)
--   count   : count of items that are dropped (for stackable items)
--   enchant : the enchant value (will be 0 for most items)
--   itemid  : itemId of the item that is dropped (NOT ObjectID as that one keeps changing all the time)
--   itemname: itemname of the item that is dropped

-- -----------------
-- GM itemtransfer (clan warehouse, freight, buyshop, sellshop, exchange, manufacture):
-- -----------------
-- type  : "transferitem"
-- action: "process"
--   process: should explain how it is transfered
-- param : "newowner - count - enchant - itemid - itemname"
--   newowner: the character or clan the item is transfered to
--   count   : count of items that are dropped (for stackable items)
--   enchant : the enchant value (will be 0 for most items)
--   itemid  : itemId of the item that is dropped (NOT ObjectID as that one keeps changing all the time)
--   itemname: itemname of the item that is dropped