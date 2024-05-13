import { useState } from "react";

export const useSelect = () => {
  const [selectedSubscription, setSelectedSubscription] = useState<string>('');
  const [selectedResourceGroup, setSelectedResourceGroup] =
    useState<string>('');
  const [selectedCluster, setSelectedCluster] = useState<string>('');

  const reset = () => {
    setSelectedCluster('');
    setSelectedResourceGroup('');
    setSelectedSubscription('');
  };

  return {
    selectedSubscription,
    setSelectedSubscription,
    selectedResourceGroup,
    setSelectedResourceGroup,
    selectedCluster,
    setSelectedCluster,
    reset,
  };
};
