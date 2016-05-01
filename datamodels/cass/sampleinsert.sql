INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 1, 40, 94086, 25, 'Claire', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 2, 60, 94086, 25, 'Thomas', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 2, 40, 94086, 25, 'Alexander', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 2, 40, 94085, 25, 'DeSilva', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 1, 40, 94085, 35, 'Jill', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 1, 40, 94084, 25, 'Jack', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 1, 40, 94087, 35, 'Craig', '2016-01-01 12:00:00');
INSERT INTO basetransaction (datereported, state, class, rank, zip, estimate, modelname, ts) VALUES ('2016-01-01', 'Texas', 1, 40, 94085, 45, 'Alice', '2016-01-01 12:00:00');

UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 1 and rank = 40  and estimate = 25;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 2 and rank = 60  and estimate = 25;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 2 and rank = 40  and estimate = 25;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 2 and rank = 40  and estimate = 25;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 1 and rank = 40  and estimate = 35;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 1 and rank = 40  and estimate = 25;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 1 and rank = 40  and estimate = 35;
UPDATE basecounter SET numberofestimators = numberofestimators + 1 WHERE datereported = '2016-01-01' AND state = 'Texas' AND class = 1 and rank = 40  and estimate = 45;



INSERT INTO sortedcounter (datereported, state, class, rank, numberofestimators, estimate) VALUES ('2016-01-01', 'Texas', 1, 40, 1, 45);
INSERT INTO sortedcounter (datereported, state, class, rank, numberofestimators, estimate) VALUES ('2016-01-01', 'Texas', 1, 40, 2, 35);
INSERT INTO sortedcounter (datereported, state, class, rank, numberofestimators, estimate) VALUES ('2016-01-01', 'Texas', 1, 40, 2, 25);

