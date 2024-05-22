import { gitExchangeApi } from '@/shared/apis';
import { Button, Col, Row, Spin } from 'antd';
import { useState } from 'react';

export const SyncButtons = () => {
  const [isSyncingDatabase, setIsSyncingDatabase] = useState(false);
  const [isSyncingGithub, setIsSyncingGithub] = useState(false);

  const handleSyncDatabaseWithGithub = async () => {
    setIsSyncingDatabase(true);
    try {
      await gitExchangeApi.syncDatabaseWithGithub();
      alert('Synced database with github successfully');
    } catch (e) {
      alert('Failed to sync database with github');
    } finally {
      setIsSyncingDatabase(false);
    }
  };

  const handleSyncGithubWithDatabase = async () => {
    setIsSyncingGithub(true);
    try {
      await gitExchangeApi.syncGithubWithDatabase();
      alert('Synced github with database successfully');
    } catch (e) {
      alert('Failed to sync github with database');
    } finally {
      setIsSyncingGithub(false);
    }
  };

  return (
    <Col span={24} className='h-fit'>
      <Row className='gap-2' justify={'center'}>
        <Button
          type="primary"
          onClick={handleSyncDatabaseWithGithub}
          style={{
            backgroundColor: isSyncingDatabase ? 'lightgray' : '#1677ff',
            width: 200,
          }}
          disabled={isSyncingDatabase || isSyncingGithub}
        >
          {isSyncingDatabase ? <Spin /> : 'Sync database with github'}
        </Button>
        <Button
          type="primary"
          onClick={handleSyncGithubWithDatabase}
          style={{
            backgroundColor: isSyncingGithub ? 'lightgray' : '#1677ff',
            width: 200,
          }}
          disabled={isSyncingGithub || isSyncingDatabase}
        >
          {isSyncingGithub ? <Spin /> : 'Sync github with database'}
        </Button>
      </Row>
    </Col>
  );
};

export default SyncButtons;
