import { Plus } from "lucide-react";
import { Button } from "@/components/ui/button";

interface AdminHeaderProps {
  title: string;
  breadcrumb?: string[];
  showCreateButton?: boolean;
  createButtonLabel?: string;
  onCreateClick?: () => void;
}

export function AdminHeader({
  title,
  breadcrumb = ["Inicio"],
  showCreateButton = false,
  createButtonLabel = "CADASTRAR PRODUTO",
  onCreateClick,
}: AdminHeaderProps) {
  return (
    <div className="flex items-center justify-between mb-6">
      <div>
        <div className="flex items-center gap-2 text-sm text-gray-500 mb-2">
          {breadcrumb.map((item, index) => (
            <span key={index}>
              {index > 0 && <span className="mx-2">{" > "}</span>}
              {item}
            </span>
          ))}
        </div>
        <h1 className="text-2xl font-bold text-gray-900">{title}</h1>
      </div>

      {showCreateButton && (
        <Button
          onClick={onCreateClick}
          className="bg-gray-900 hover:bg-gray-800 text-white"
        >
          <Plus className="w-4 h-4 mr-2" />
          {createButtonLabel}
        </Button>
      )}
    </div>
  );
}

